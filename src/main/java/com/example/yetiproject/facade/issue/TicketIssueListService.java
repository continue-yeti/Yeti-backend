package com.example.yetiproject.facade.issue;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.facade.repository.RedisRepository;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.example.yetiproject.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j(topic = "TicketIssueService")
@Service
@RequiredArgsConstructor
public class TicketIssueListService {
	private final RedisRepository redisRepository;
	private final TicketService ticketService;
	private final ObjectMapper objectMapper;
	private final TicketInfoRepository ticketInfoRepository;
	private static final long PUBLISH_SIZE = 100;
	private static final long LAST_INDEX = 1;

	private final String USER_QUEUE_WAIT_KEY = "ticketInfo:queue:%s:wait";
	private final String TICKETINFO_STOCK_COUNT = "ticketInfo:%s:stock";


	@Transactional
	public void publish(String key) throws JsonProcessingException {
		final long end = PUBLISH_SIZE - LAST_INDEX;

		for (int i = 0; i <= end; i++) {
			String ticketRequest = redisRepository.listLeftPop(USER_QUEUE_WAIT_KEY.formatted(key));
			TicketRequestDto ticketRequestDto = objectMapper.readValue(ticketRequest, TicketRequestDto.class);

			if (Integer.parseInt(redisRepository.get(TICKETINFO_STOCK_COUNT.formatted(key))) ==
				ticketInfoRepository.getStockforTicketInfo(ticketRequestDto.getTicketInfoId())
			) {
				log.info("[ticketInfo : " + ticketRequestDto.getTicketInfoId() + " 은 매진입니다.]");
				return;
			}

			//reserve
			ticketService.reserveTicketQueue(ticketRequestDto.getUserId(), ticketRequestDto);

			log.info("[예매완료] UserID = {} , seat = {}", ticketRequestDto.getUserId(),
				ticketRequestDto.getSeat());

			increase(ticketRequestDto.getTicketInfoId());

			log.info("발행된 티켓 수 " + redisRepository.get(TICKETINFO_STOCK_COUNT.formatted(ticketRequestDto.getTicketInfoId())));
		}


	}

	public Long decrease(Long ticketInfoId){
		return redisRepository.decrease(TICKETINFO_STOCK_COUNT.formatted(ticketInfoId));
	}
	public Long increase(Long ticketInfoId) {
		return redisRepository.increase(TICKETINFO_STOCK_COUNT.formatted(ticketInfoId));
	}
}
