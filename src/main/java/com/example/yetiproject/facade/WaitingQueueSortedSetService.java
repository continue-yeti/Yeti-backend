package com.example.yetiproject.facade;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.entity.TicketInfo;
import com.example.yetiproject.facade.repository.RedisRepository;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "WaitingQueueSortedSetService")
@Service
@RequiredArgsConstructor
public class WaitingQueueSortedSetService {
	private final RedisRepository redisRepository;
	private final TicketInfoRepository ticketInfoRepository;

	private final ObjectMapper objectMapper;
	private static final long FIRST_ELEMENT = 0;
	private static final long LAST_ELEMENT = -1;

	public void registerQueue(Long userId, TicketRequestDto ticketRequestDto) throws JsonProcessingException {
		// 오픈날짜 종료날짜를 체크한다.
		// if(checkTicketInfoDate(ticketRequestDto.getTicketInfoId()) == false){
		// 	log.info("예매가능한 날짜가 아닙니다.");
		// 	return;
		// }
		// redis ticketInfo check
		setTicketStock(ticketRequestDto.getTicketInfoId());

		//객체 -> String 변형
		ticketRequestDto.setUserId(userId); //user
		String jsonObject = objectMapper.writeValueAsString(ticketRequestDto); //ticketRequestDto -> String

		final long now = System.currentTimeMillis();
		log.info("대기열에 추가 - userId : {} requestDto : {} ({}초)", userId, jsonObject, System.currentTimeMillis());
		redisRepository.zAddIfAbsent("ticket", jsonObject, now);
	}

	public void setTicketStock(Long ticketInfoId) throws JsonProcessingException {
		// Redis에 해당 stock가 없으면 redis에 넣기
		TicketInfo ticketInfo = ticketInfoRepository.findById(ticketInfoId).get();
		if(redisRepository.get("ticketInfo"+ticketInfoId)==null){
			// Redis에 해당 stock가 없으면 redis에 넣는다.
			redisRepository.set("ticketInfo"+ticketInfoId, String.valueOf(ticketInfo.getStock()));
		}
	}

	public void getWaitingNumber() throws JsonProcessingException {
		final long start = FIRST_ELEMENT;
		final long end = LAST_ELEMENT;

		Set<String> queue = redisRepository.zRange("ticket", start, end);

		for ( String ticketRequest : queue) {
			Long rank = redisRepository.zRank("ticket", ticketRequest);
			log.info("'{}'님의 현재 대기열은 {}명 남았습니다.", ticketRequest, rank);
		}
	}

	private Boolean checkTicketInfoDate(Long ticketInfoId){
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime openDate = ticketInfoRepository.findById(ticketInfoId).get().getOpenDate();
		LocalDateTime closeDate = ticketInfoRepository.findById(ticketInfoId).get().getCloseDate();

		log.info(String.valueOf(ChronoUnit.SECONDS.between(openDate, today)));
		log.info(String.valueOf((ChronoUnit.SECONDS.between(today, closeDate))));

		if(
			(ChronoUnit.SECONDS.between(openDate, today) > 0) &&
				(ChronoUnit.SECONDS.between(today, closeDate) > 0)
		){
			log.info("티켓 예약이 가능합니다.");
			return true;
		}
		return false;
	}
}
