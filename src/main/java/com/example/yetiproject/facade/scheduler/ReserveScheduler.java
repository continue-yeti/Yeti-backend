package com.example.yetiproject.facade.scheduler;

import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.yetiproject.facade.TicketIssueService;
import com.example.yetiproject.facade.WaitingQueueService;
import com.example.yetiproject.facade.WaitingQueueSortedSetService;
import com.example.yetiproject.facade.service.QueueSseService;
import com.example.yetiproject.repository.EmitterRepository;
import com.example.yetiproject.repository.EmitterRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ReserveScheduler")
@Component
@RequiredArgsConstructor
public class ReserveScheduler {
	private final WaitingQueueSortedSetService waitingQueueSortedSetService;
	private final TicketIssueService ticketIssueService;
	private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();

	@Scheduled(fixedDelay = 1000)
	private void ticketReserveScheduler() throws JsonProcessingException {
		log.info("======== 예매가 시작됩니다.==========");
		waitingQueueSortedSetService.getWaitingNumber();
		ticketIssueService.publish();

	}

}
