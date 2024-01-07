package com.example.yetiproject.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.example.yetiproject.entity.Ticket;
import org.springframework.stereotype.Service;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.entity.TicketInfo;
import com.example.yetiproject.facade.repository.RedisRepository;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.example.yetiproject.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "TicketIssueService")
@Service
@RequiredArgsConstructor
public class TicketIssueService {
	private final RedisRepository redisRepository;
	private final TicketService ticketService;
	private final ObjectMapper objectMapper;
	private final TicketInfoRepository ticketInfoRepository;
	private static final long FIRST_ELEMENT = 0;
	private static final long PUBLISH_SIZE = 100;
	private static final long LAST_INDEX = 1;

	private final String USER_QUEUE_WAIT_KEY = "ticketInfo:queue:%s:wait";
	private final String TICKETINFO_STOCK_COUNT = "ticketInfo:%s:stock";

	@Transactional
	public void publish(String key) throws JsonProcessingException {
		log.info("Publish ticketInfoId" + key);
		final long start = FIRST_ELEMENT;
		final long end = PUBLISH_SIZE - LAST_INDEX;

		Set<String> queue = redisRepository.zRange(USER_QUEUE_WAIT_KEY.formatted(key), start, end);

		for(String ticketRequest : queue){
			TicketRequestDto ticketRequestDto = objectMapper.readValue(ticketRequest, TicketRequestDto.class);

			if (Integer.parseInt(redisRepository.get(TICKETINFO_STOCK_COUNT.formatted(key))) == 0) {
				log.info("[ticketInfo : " + ticketRequestDto.getTicketInfoId() + " 은 매진입니다.]");
				return;
			}

			//reserve
			ticketService.reserveTicketSortedSet(ticketRequestDto.getUserId(), ticketRequestDto);

			log.info("[예매완료] UserID = {} , posX = {}, poxY = {}", ticketRequestDto.getUserId(),
				ticketRequestDto.getPosX(), ticketRequestDto.getPosY());

			log.info(ticketRequest);
			log.info(USER_QUEUE_WAIT_KEY.formatted(key));
			redisRepository.zRemove(USER_QUEUE_WAIT_KEY.formatted(key), ticketRequest);

			decrease(ticketRequestDto.getTicketInfoId());
			log.info("남은 티켓 수 " + redisRepository.get(TICKETINFO_STOCK_COUNT.formatted(ticketRequestDto.getTicketInfoId())));

		}
	}

	@Transactional
	public void publishBluk(String key) throws JsonProcessingException {
		final long start = FIRST_ELEMENT;
		final long end = PUBLISH_SIZE - LAST_INDEX;

		Set<String> queue = redisRepository.zRange("ticket", start, end);
		List<TicketRequestDto> ticketRequestDtoList = new ArrayList<>();

		for (String ticketRequest : queue) {
			TicketRequestDto ticketRequestDto = objectMapper.readValue(ticketRequest, TicketRequestDto.class);

			if (Integer.parseInt(redisRepository.get(TICKETINFO_STOCK_COUNT.formatted(key))) == 0) {
				log.info("[ticketInfo : " + ticketRequestDto.getTicketInfoId() + " 은 매진입니다.]");
				return;
			}

			ticketRequestDtoList.add(ticketRequestDto);
			//reserve
			ticketService.reserveTicketSortedSet(ticketRequestDto.getUserId(), ticketRequestDto);

			log.info("[예매완료] UserID = {} , posX = {}, poxY = {}", ticketRequestDto.getUserId(),
				ticketRequestDto.getPosX(), ticketRequestDto.getPosY());
			redisRepository.zRemove(USER_QUEUE_WAIT_KEY.formatted(key), ticketRequest);

			decrease(ticketRequestDto.getTicketInfoId());
			log.info("남은 티켓 수 " + redisRepository.get("ticketInfo" + ticketRequestDto.getTicketInfoId()));
		}

		// 티켓 일괄 발급
		ticketService.reserveTicketsInBatch(ticketRequestDtoList);
	}

	public Long decrease(Long ticketInfoId){
		return redisRepository.decrease(TICKETINFO_STOCK_COUNT.formatted(ticketInfoId));
	}
}
