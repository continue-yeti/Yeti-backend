package com.example.yetiproject.facade;

import java.time.Instant;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.facade.repository.RedisRepository;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.example.yetiproject.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j(topic = "WaitingQueueService")
@Service
@RequiredArgsConstructor
public class WaitingQueueService {
    private final RedisRepository redisRepository;
    private final TicketService ticketService;
    private final TicketInfoRepository ticketInfoRepository;
    private final ObjectMapper objectMapper;

    private static final long FIRST_ELEMENT = 0;
    private static final long LAST_ELEMENT = -1;

    private final String USER_QUEUE_WAIT_KEY = "ticketInfo:queue:%s:wait";

    private final String TICKETINFO_STOCK_COUNT = "ticketInfo:%s:stock";


    // Queue에 추가
    public Long registerQueue(Long userId, TicketRequestDto ticketRequestDto) throws JsonProcessingException {
        // redis ticketInfo check
        setTicketStock(ticketRequestDto.getTicketInfoId());

        ticketRequestDto.setUserId(userId); //user 추가
        String jsonObject = objectMapper.writeValueAsString(ticketRequestDto);
        double timeStamp = Instant.now().getEpochSecond();
        log.info("대기열에 추가 - userId : {} requestDto : {} ({}초)", ticketRequestDto.getUserId(), jsonObject, System.currentTimeMillis());

        redisRepository.zAddIfAbsent(USER_QUEUE_WAIT_KEY.formatted(ticketRequestDto.getTicketInfoId()), jsonObject, timeStamp);
        return redisRepository.zRank(USER_QUEUE_WAIT_KEY.formatted(ticketRequestDto.getTicketInfoId()),jsonObject);
    }

    private void setTicketStock(Long ticketInfoId) throws JsonProcessingException {
        // Redis에 해당 갯수를 0으로 셋팅한다.
        if(redisRepository.get(TICKETINFO_STOCK_COUNT.formatted(ticketInfoId))==null){
            // Redis에 해당 stock 0으로 초기화해준다.
            redisRepository.set(TICKETINFO_STOCK_COUNT.formatted(ticketInfoId), String.valueOf(0));
        }
    }

    public void getWaitingNumber(Long ticketInfoId) {
        final long start = FIRST_ELEMENT;
        final long end = LAST_ELEMENT;

        Set<String> queue = redisRepository.zRange(TICKETINFO_STOCK_COUNT.formatted(ticketInfoId), start, end);

        // 대기열 상황
        for (String data : queue) {
            Long rank = redisRepository.zRank(TICKETINFO_STOCK_COUNT.formatted(ticketInfoId), data);
           //log.info("'{}'님의 현재 대기열은 {}명 남았습니다.", data, rank);
        }
    }

}
