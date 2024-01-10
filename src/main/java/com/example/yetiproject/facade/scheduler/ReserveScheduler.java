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
	private final RedisTemplate<String, String> redisTemplate;
	private final TicketIssueSortedSetService ticketIssueSortedSetService;

	private final String USER_QUEUE_WAIT_KEY_FOR_SCAN = "ticketInfo:queue:*:wait";

	@Scheduled(initialDelay = 5000, fixedDelay = 3000)
	private void ticketReserveScheduler() throws JsonProcessingException {
		log.info("start scheduling...");

		ScanOptions options = ScanOptions.scanOptions().match(USER_QUEUE_WAIT_KEY_FOR_SCAN).build();
		try(Cursor<String> cursor = redisTemplate.scan(options)){
			while(cursor.hasNext()){
				String key = cursor.next();
				System.out.println("Found key : " + key);
				System.out.println("ticketInfo : " + key.split(":")[2]);

				ticketIssueSortedSetService.publish(key.split(":")[2]);
			}
		}
	}

}
