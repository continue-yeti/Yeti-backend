package com.example.yetiproject.facade.sortedset;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.exception.ErrorCode;
import com.example.yetiproject.facade.repository.RedisRepository;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j(topic = "WaitingQueueListService")
@Service
@RequiredArgsConstructor
public class WaitingQueueListService {
    private final RedisRepository redisRepository;
    private final TicketInfoRepository ticketInfoRepository;
    private final ObjectMapper objectMapper;

    private final String USER_QUEUE_WAIT_KEY = "ticketInfo:queue:%s:wait";
    private final String TICKETINFO_OCCUPY_SEAT = "ticketInfo:%s:reserved:seat";
    private final String TICKETINFO_STOCK_COUNT = "ticketInfo:%s:stock";


    public Long registerQueue(Long userId, TicketRequestDto ticketRequestDto) throws JsonProcessingException {
        //오픈날짜 종료날짜를 체크한다.
        if(checkTicketInfoDate(ticketRequestDto.getTicketInfoId()) == false){
            log.info("예매가능한 날짜가 아닙니다.");
            throw ErrorCode.NOT_AVAILABLE_RESERVATION_DATES.build();
        }
        // 좌석 체크
        if(checkSelectedSeat(TICKETINFO_OCCUPY_SEAT.formatted(ticketRequestDto.getTicketInfoId())
            , ticketRequestDto.getSeat(), userId) == false){
            log.info("이미 선택된 좌석입니다.");
            throw ErrorCode.QUEUE_ALREADY_REGISTERED_USER.build();
        }

        // redis ticketInfo check
        setTicketStock(ticketRequestDto.getTicketInfoId());
        ticketRequestDto.setUserId(userId); //user 추가

        String jsonObject = objectMapper.writeValueAsString(ticketRequestDto);
        log.info("대기열에 추가 - userId : {} requestDto : {}", ticketRequestDto.getUserId(), jsonObject);

        redisRepository.listRightPush(USER_QUEUE_WAIT_KEY.formatted(ticketRequestDto.getTicketInfoId()), jsonObject);
        return redisRepository.indexOfRank(USER_QUEUE_WAIT_KEY.formatted(ticketRequestDto.getTicketInfoId()),jsonObject)+1;
    }

    private void setTicketStock(Long ticketInfoId) throws JsonProcessingException {
        // Redis에 해당 갯수를 0으로 셋팅한다.
        if(redisRepository.get(TICKETINFO_STOCK_COUNT.formatted(ticketInfoId))==null){
            // Redis에 해당 stock 0으로 초기화해준다.
            redisRepository.set(TICKETINFO_STOCK_COUNT.formatted(ticketInfoId), String.valueOf(0));
        }
    }

    private Boolean checkTicketInfoDate(Long ticketInfoId){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime openDate = ticketInfoRepository.findById(ticketInfoId).get().getOpenDate();
        LocalDateTime closeDate = ticketInfoRepository.findById(ticketInfoId).get().getCloseDate();

        if((ChronoUnit.SECONDS.between(openDate, today) > 0) &&
            (ChronoUnit.SECONDS.between(today, closeDate) > 0)){
            return true;
        }
        return false;
    }

    private boolean checkSelectedSeat(String key, String seat, Long userId){
        return redisRepository.hashSetNx(key, seat, userId);
    }

}
