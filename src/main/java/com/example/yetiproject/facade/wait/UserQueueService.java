package com.example.yetiproject.facade.wait;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.entity.Seat;
import com.example.yetiproject.entity.User;
import com.example.yetiproject.exception.ErrorCode;
import com.example.yetiproject.facade.repository.RedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "UserQueueService")
@Service
@RequiredArgsConstructor
public class UserQueueService {
	private final RedisRepository redisRepository;
	private final ObjectMapper objectMapper;
	private final String USER_QUEUE_WAIT_KEY = "ticketInfo:queue:%s:wait";
	private final String USER_QUEUE_WAIT_KEY_FOR_SCAN = "ticketInfo:queue:*:wait";
	private final String USER_QUEUE_PROCEED_KEY = "ticketInfo:queue:%s:proceed";
	private final String TICKETINFO_OCCUPY_SEAT = "ticketInfo:%s:reserved:seat";


	public Long registerWaitQueue(TicketRequestDto ticketRequestDto) throws JsonProcessingException {
		// 좌석 체크
		String seatObject = objectMapper.writeValueAsString(new Seat(ticketRequestDto.getPosX(), ticketRequestDto.getPosY()));
		if(checkSelectedSeat(TICKETINFO_OCCUPY_SEAT.formatted(ticketRequestDto.getTicketInfoId())
			, seatObject, ticketRequestDto.getUserId()) == false){
			log.info("이미 선택된 좌석입니다.");
			throw ErrorCode.QUEUE_ALREADY_REGISTERED_USER.build();
		}

		log.info("예매 가능한 좌석입니다.");

		String jsonObject = objectMapper.writeValueAsString(ticketRequestDto);
		double timeStamp = Instant.now().getEpochSecond();

		log.info("대기열에 추가 - userId : {} requestDto : {} ({}초)", ticketRequestDto.getUserId(), jsonObject, timeStamp);
		redisRepository.zAddIfAbsent(USER_QUEUE_WAIT_KEY.formatted(ticketRequestDto.getTicketInfoId()), jsonObject, timeStamp);

		return redisRepository.zRank(USER_QUEUE_WAIT_KEY.formatted(ticketRequestDto.getTicketInfoId()),jsonObject);
	}

	public boolean checkSelectedSeat(String key, String seat, Long userId){
		return redisRepository.hashSetNx(key, seat, userId);
	}

}
