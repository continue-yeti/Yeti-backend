package com.example.yetiproject.facade.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.yetiproject.facade.TicketIssueService;
import com.example.yetiproject.facade.WaitingQueueService;
import com.example.yetiproject.facade.WaitingQueueSortedSetService;
import com.example.yetiproject.facade.repository.RedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ReserveScheduler")
@Component
@RequiredArgsConstructor
public class ReserveScheduler {
	private final RedisRepository redisRepository;
	private final WaitingQueueSortedSetService waitingQueueSortedSetService;
	private final TicketIssueService ticketIssueService;

	private final String USER_QUEUE_WAIT_KEY_FOR_SCAN = "ticketInfo:queue:*:wait";

	@Scheduled(initialDelay = 5000, fixedDelay = 3000)
	private void ticketReserveScheduler() throws JsonProcessingException {
		log.info("======== 예매가 시작됩니다.==========");
		ticketIssueService.publish();
	}

}
