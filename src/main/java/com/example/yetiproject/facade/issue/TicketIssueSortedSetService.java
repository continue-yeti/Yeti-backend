package com.example.yetiproject.facade.issue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.facade.repository.RedisRepository;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.example.yetiproject.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "TicketIssueSortedSetService")
@Service
@RequiredArgsConstructor
public class TicketIssueSortedSetService {
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
	public void waitingQueue(String key) throws JsonProcessingException {
		final long start = FIRST_ELEMENT;
		final long end = PUBLISH_SIZE - LAST_INDEX;

		// 대기열 조회
		Set<String> queue = redisRepository.zRange(USER_QUEUE_WAIT_KEY.formatted(key), start, end);
		for(String ticketRequest : queue){
			TicketRequestDto ticketRequestDto = objectMapper.readValue(ticketRequest, TicketRequestDto.class); // json String > json Object

			// 매진 확인
			if (Integer.parseInt(redisRepository.get(TICKETINFO_STOCK_COUNT.formatted(key))) ==
				ticketInfoRepository.getStockforTicketInfo(ticketRequestDto.getTicketInfoId())
			) {
				//log.info("[ticketInfo : " + ticketRequestDto.getTicketInfoId() + " 은 매진입니다.]");
				return;
			}
			// 대기열에서 요소 제거
			redisRepository.zRemove(USER_QUEUE_WAIT_KEY.formatted(key), ticketRequest);

			// 예매
			ticketService.reserveTicketSortedSet(ticketRequestDto.getUserId(), ticketRequestDto);

			// log.info("[예매완료] UserID = {} , posX = {}, poxY = {}", ticketRequestDto.getUserId(),
				//ticketRequestDto.getPosX(), ticketRequestDto.getPosY());
			//log.info("예약된 티켓 수 " + redisRepository.get(TICKETINFO_STOCK_COUNT.formatted(ticketRequestDto.getTicketInfoId())));
			return;
		}
	}

	@Transactional
	public void waitingQueueBulk(String key) throws JsonProcessingException {
		final long start = FIRST_ELEMENT;
		final long end = PUBLISH_SIZE - LAST_INDEX;

		List<TicketRequestDto> ticketRequestDtoList = new ArrayList<>();

		// 대기열 조회
		Set<String> queue = redisRepository.zRange(USER_QUEUE_WAIT_KEY.formatted(key), start, end);
		for (String ticketRequest : queue) {
			TicketRequestDto ticketRequestDto = objectMapper.readValue(ticketRequest, TicketRequestDto.class);
			ticketRequestDtoList.add(ticketRequestDto);

			// 대기열에서 요소 제거
			redisRepository.zRemove(USER_QUEUE_WAIT_KEY.formatted(key), ticketRequest);
		}

		// 티켓 일괄 발급
		ticketService.reserveTicketsInBatch(ticketRequestDtoList);
	}


}
