package com.example.yetiproject.facade;

import java.util.Set;

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


	@Transactional
	public void publish() throws JsonProcessingException {
		final long start = FIRST_ELEMENT;
		final long end = PUBLISH_SIZE - LAST_INDEX;

		Set<String> queue = redisRepository.zRange("ticket", start, end);

		for (String ticketRequest : queue) {
			TicketRequestDto ticketRequestDto = objectMapper.readValue(ticketRequest, TicketRequestDto.class);
			ticketService.reserveTicketSortedSet(ticketRequestDto.getUserId(), ticketRequestDto); //티켓발행

			log.info("[예매완료] UserID = {} , posX = {}, poxY = {}", ticketRequestDto.getUserId(),
				ticketRequestDto.getPosX(), ticketRequestDto.getPosY());
			redisRepository.zRemove("ticket", ticketRequest);

			if (Integer.parseInt(redisRepository.get("ticketInfo" + ticketRequestDto.getTicketInfoId())) == 0) {
				ticketInfoRepository.findById(ticketRequestDto.getTicketInfoId()).get().updateStockCount(0L);
				log.info("[ticketInfo : " + ticketRequestDto.getTicketInfoId() + " 은 매진입니다.]");
				redisRepository.delete("ticket"); //destory
				return;
			}
			decrease(ticketRequestDto.getTicketInfoId());
			log.info("남은 티켓 수 " + redisRepository.get("ticketInfo" + ticketRequestDto.getTicketInfoId()));
		}

	}

	public Long decrease(Long ticketInfoId){
		return redisRepository.decrease("ticketInfo"+ticketInfoId);
	}
}
