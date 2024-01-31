package com.example.yetiproject.facade.scheduler;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.yetiproject.facade.issue.TicketIssueSortedSetService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ReserveScheduler")
@Component
@RequiredArgsConstructor
public class ReserveScheduler {
	private final TicketIssueSortedSetService ticketIssueSortedSetService;

	@Scheduled(initialDelay = 5000, fixedDelay = 1000)
	private void ticketReserveScheduler() throws JsonProcessingException {
		ticketIssueSortedSetService.processQueueAsync();
	}

}
