package com.example.yetiproject.facade;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.entity.TicketInfo;
import com.example.yetiproject.entity.User;
import com.example.yetiproject.exception.entity.TicketInfo.TicketInfoNotFoundException;
import com.example.yetiproject.facade.repository.RedisRepository;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.example.yetiproject.repository.UserRepository;
import com.example.yetiproject.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Set;


@Slf4j
@Service
@RequiredArgsConstructor
public class WaitingQueueService {
    private final RedisRepository redisRepository;
    private final TicketService ticketService;
    private final TicketInfoRepository ticketInfoRepository;
    private final UserRepository userRepository;

    private static final long FIRST_ELEMENT = 0;
    private static final long LAST_ELEMENT = -1;
    private static final long PUBLISH_SIZE = 100;
    private static final String KEY = "ticketSorted";
    private static final String COUNT_KEY = "ticketSortedCount";
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Queue에 추가
    public void addQueue(User user, TicketRequestDto requestDto) throws JsonProcessingException {
        // DTO 객체를 JSON 문자열로 변환
        final long now = System.currentTimeMillis();
        LocalDateTime nowTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(now), ZoneId.systemDefault());

        requestDto.setUserId(user.getUserId());
        requestDto.setNow(now);
        String jsonString = objectMapper.writeValueAsString(requestDto);

        // redis에 저장
        redisRepository.zAddIfAbsent(KEY, jsonString, now);

//        log.info("대기열에 추가 - Value : {} ({}초)", jsonString, nowTime);
    }

//    @Scheduled(fixedDelay = 1000) // 1초마다 반복
//    public void reserveTicket() throws JsonProcessingException {
////        log.info("Redis sorted set queue");
//        getOrder();
//        publish();
//    }

    // 대기열 조회
    public void getOrder() {
        // Redis Sorted Set에서 가져올 범위 설정
        final long start = FIRST_ELEMENT;
        final long end = LAST_ELEMENT;

        // Redis Sorted Set에서 범위 내의 멤버들을 가져옴
        Set<String> queue = redisRepository.zRange(KEY, start, end);

        // 대기열 상황
        for (String data : queue) {
            Long rank = redisRepository.zRank(KEY, data);
//            log.info("'{}'님의 현재 대기열은 {}명 남았습니다.", data, rank);
        }
    }

    // ticket 발급
    public void publish() throws JsonProcessingException {
        // Redis Sorted Set에서 가져올 범위 설정
        final long start = FIRST_ELEMENT;
        final long end = PUBLISH_SIZE - 1;

        // Redis Sorted Set에서 범위 내의 멤버들을 가져옴
        Set<String> queues = redisRepository.zRange(KEY, start, end);

        // 발급 시작
        for (String queue : queues) {
            // String Object > QueueObject
            QueueObject queueObject = objectMapper.readValue(queue, QueueObject.class);

            // ticketInfo의 정보 가져오기
            TicketInfo ticketInfo = ticketInfoRepository.findById(queueObject.getTicketInfoId())
                    .orElseThrow(() -> new TicketInfoNotFoundException("티켓 정보를 찾을 수 없습니다."));

            // 해당 티켓 정보에 속한 대기열의 크기 가져오기
            Long ticketCount = getTicketCounter(COUNT_KEY+ticketInfo.getTicketInfoId());
//            log.info("ticket Count : {}", ticketCount);

            if (ticketCount >= ticketInfo.getStock()) {
                LocalDateTime nowTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
//                log.info("==== 티켓이 매진되었습니다. ====");
//                log.info("queue end : {}", nowTime);
//                break;
                return;
            }

            // 티켓 발급을 위한 TicketRequestDto 생성
            TicketRequestDto ticketRequestDto = TicketRequestDto.builder()
                    .ticketInfoId(queueObject.getTicketInfoId())
                    .posX(queueObject.getPosX())
                    .posY(queueObject.getPosY())
                    .build();
            // 티켓 발급을 위한 User build
            User user = User.builder().userId(queueObject.getUserId()).build();

            // 티켓 발급
            ticketService.reserveTicketQueue(user, ticketRequestDto);
            // 티켓 개수 증가
            incrementTicketCounter(COUNT_KEY + queueObject.getTicketInfoId().toString());
            // 대기열 제거
            redisRepository.zRemove(KEY, queue);
        }
    }

    // 예매한 티켓 개수 증가
    public void incrementTicketCounter(String key) {
        // ValueOperations를 이용하여 INCR 명령어 실행
        redisRepository.increase(key);
    }

    // 예매한 티켓 개수 GET
    public Long getTicketCounter(String key) {
        // GET 명령어 실행 후 값을 가져오기
        String ticketCountString = redisRepository.get(key);
        Long ticketCount;

        if (Objects.equals(ticketCountString, null)) {
            // Redis에 해당 stock가 없으면 redis에 넣는다.
            redisRepository.set(key, "0");
            ticketCountString = redisRepository.get(key); // 처음에 초기화 에러 방지
        }

        ticketCount = Long.parseLong(ticketCountString);
        return ticketCount;
    }
}
