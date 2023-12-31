package com.example.yetiproject.facade;

import com.example.yetiproject.auth.security.UserDetailsImpl;
import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.entity.TicketInfo;
import com.example.yetiproject.entity.User;
import com.example.yetiproject.exception.entity.Ticket.TicketDuplicateSeatException;
import com.example.yetiproject.exception.entity.TicketInfo.TicketInfoNotFoundException;
import com.example.yetiproject.facade.repository.RedisRepository;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.example.yetiproject.repository.TicketRepository;
import com.example.yetiproject.repository.UserRepository;
import com.example.yetiproject.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaitingQueueService {
    //private final RedisTemplate<String, String> redisTemplate;
    //    private final TicketScheduler ticketScheduler;
    private final RedisRepository redisRepository;
    private final TicketService ticketService;
    private final TicketInfoRepository ticketInfoRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    private static final long FIRST_ELEMENT = 0;
    private static final long LAST_ELEMENT = -1;
    private static final long PUBLISH_SIZE = 10;
    private long publishSize = 100;
    private static final String KEY = "ticket";
    private static final String COUNT_KEY = "ticket_count";
    private final ObjectMapper objectMapper;

    // Queue에 추가
    public Boolean registerQueue(User user, TicketRequestDto ticketRequestDto) throws JsonProcessingException {
        // 해당 TicketInfo 객체를 생성한다.
        TicketInfo ticketInfo = ticketInfoRepository.findById(ticketRequestDto.getTicketInfoId()).get();

        // 오픈날짜 종료날짜를 체크한다.
        if(checkTicketInfoDate(ticketInfo.getOpenDate(), ticketInfo.getCloseDate()) == false){
            log.info("예매가능한 날짜가 아닙니다.");
            return false;
        }

        // redis ticketInfo check
        setTicketStock(ticketInfo.getTicketInfoId(), ticketInfo.getStock());

        // DTO 객체를 JSON 문자열로 반환
        // TODO. Controller에서 다른곳에서 User을 쓰는지 보고 안쓰면 userDetails 객체가 아닌 아이디만 넘어오도록 해야한다.
        ticketRequestDto.setUserId(user.getUserId());
        String jsonObject = objectMapper.writeValueAsString(ticketRequestDto);

        final double now = System.currentTimeMillis();
        // redis에 저장
        log.info("대기열에 추가 - Key : {}  Value : {} ({}초)", KEY, jsonObject, now);
        return redisRepository.zAddIfAbsent("ticket", jsonObject, now);
    }

    public void setTicketStock(Long ticketInfoId, Long stock) throws JsonProcessingException {
        // Redis에 해당 stock가 없으면 redis에 넣기
        if(redisRepository.get("ticketInfo"+ticketInfoId)==null){
            // Redis에 해당 stock가 없으면 redis에 넣는다.
            redisRepository.set("ticketInfo"+ticketInfoId, String.valueOf(stock));
            redisRepository.set("ticketInfo"+ticketInfoId+"_cnt", String.valueOf(0));
        }
        log.info("waitingQueueService" + "ticketInfo" + ticketInfoId + " : setting");
    }

//    @Scheduled(fixedDelay = 1000) // 1초마다 반복
//    public void reserveTicket() throws JsonProcessingException {
//        // 동적으로 publishSize를 조절
////        Long requestSize = redisTemplate.opsForZSet().zCard(KEY);
////        if (requestSize > 1000) {
////            setPublishSize(1000);
////        } else if (requestSize > 100) {
////            setPublishSize(100);
////        } else {
////            setPublishSize(10);
////        }
//        publish();
//        getOrder();
//    }

    // 대기열 생성
    public void getOrder(){
        // Redis Sorted Set에서 가져올 범위 설정
        final long start = FIRST_ELEMENT;
        final long end = LAST_ELEMENT;

        // Redis Sorted Set에서 범위 내의 멤버들을 가져옴
        //Set<String> queue = redisTemplate.opsForZSet().range(KEY, start, end);
        Set<String> queue = redisRepository.zRange(KEY, start, end);
        // 대기열 상황
        for (String data : queue) {
            //Long rank = redisTemplate.opsForZSet().rank(KEY, data);
            Long rank = redisRepository.zRank(KEY, data);
            log.info("'{}'님의 현재 대기열은 {}명 남았습니다.", data, rank);
        }
    }

    // ticket 발급
    public void publish() throws JsonProcessingException {
        // Redis Sorted Set에서 가져올 범위 설정
        final long start = FIRST_ELEMENT;
        final long end = publishSize - 1;

        Set<String> queues = redisRepository.zRange(KEY, start, end);

        // 발급 시작
        for (String ticketRequest : queues) {
            // JSON 문자열을 ticketRequestDto로 바꾼다.
            TicketRequestDto ticketRequestDto = objectMapper.readValue(ticketRequest, TicketRequestDto.class);

            //log.info(redisRepository.get("ticketInfo"+ticketRequestDto.getTicketInfoId()+"_cnt"));
            //log.info(redisRepository.get("ticketInfo"+ticketRequestDto.getTicketInfoId()));

            if ( Integer.parseInt(redisRepository.get("ticketInfo"+ticketRequestDto.getTicketInfoId()+"_cnt")) ==
                Integer.parseInt(redisRepository.get("ticketInfo"+ticketRequestDto.getTicketInfoId())) ){
                log.info("매진입니다.");
                return;
            }

            // 티켓 발급
            User user = User.builder().userId(ticketRequestDto.getUserId()).build();
            ticketService.reserveTicketQueue(user, ticketRequestDto);
            // 티켓 개수 증가
            incrementTicketCounter("ticketInfo" + ticketRequestDto.getTicketInfoId() + "_cnt");
            /*
            log.info("'{}'님의 {}번 티켓이 발급되었습니다 (좌석 : {}, {})",
                    user.getUserId(),
                    queueData.getTicketInfoId(),
                    queueData.getPosX(),
                    queueData.getPosY());*/
            // 대기열 제거
            redisRepository.zRemove(KEY, ticketRequest);
            return;
        }
    }

    // 예매한 티켓 개수 증가
    public Long incrementTicketCounter(String key) {
        return redisRepository.increase(key);
    }

    // 예매한 티켓 개수 GET
    // public Long getTicketCounter(String key) {
    //     // ValueOperations를 이용하여 GET 명령어 실행
    //     ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
    //
    //     // GET 명령어 실행 후 값을 가져오기
    //     String stringValue = valueOps.get(key);
    //
    //     Long ticketCount;
    //     if (stringValue == null) {
    //         // 키가 없을 경우 초기값 설정
    //         ticketCount = 0L;
    //     } else {
    //         ticketCount = Long.parseLong(stringValue);
    //     }
    //     return ticketCount;
    // }

    // // 요청 수에 따른 동적으로 처리 개수 변경
    // public void setPublishSize(int publishSize) {
    //     this.publishSize = publishSize;
    // }

    //오픈날짜 종료날짜를 확인
    public Boolean checkTicketInfoDate(LocalDateTime openDate, LocalDateTime closeDate){
        LocalDateTime today = LocalDateTime.now(); //현재시간

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
