package com.example.yetiproject.facade;

import com.example.yetiproject.auth.security.UserDetailsImpl;
import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.dto.ticket.TicketResponseDto;
import com.example.yetiproject.entity.Ticket;
import com.example.yetiproject.entity.TicketInfo;
import com.example.yetiproject.entity.User;
import com.example.yetiproject.exception.entity.TicketInfo.TicketInfoNotFoundException;
import com.example.yetiproject.exception.entity.user.UserNotFoundException;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.example.yetiproject.repository.UserRepository;
import com.example.yetiproject.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WaitingQueueService {
    private final RedisTemplate<String, Object> redisTemplate;
//    private final TicketScheduler ticketScheduler;
    private final TicketService ticketService;
    private final TicketInfoRepository ticketInfoRepository;
    private final UserRepository userRepository;

    private static final long FIRST_ELEMENT = 0;
    private static final long LAST_ELEMENT = -1;
    private static final long PUBLISH_SIZE = 10;

    private QueueObject scheduledQueueObject;

    // Queue에 추가
    public void addQueue(UserDetailsImpl userDetails, TicketRequestDto requestDto){
        Long ticketInfoId = requestDto.getTicketInfoId();
        QueueObject queueObject = new QueueObject(userDetails, requestDto);
        final long now = System.currentTimeMillis();
        redisTemplate.opsForZSet().add(ticketInfoId.toString(), queueObject, (int)now);
        log.info("대기열에 추가 - {} ({}초)", queueObject, now);

        // 대기열 등록
//        ticketScheduler.reserveTicket(queueObject); // TicketScheduler.class 안쓰면 삭제
        setScheduledQueueObject(queueObject);
    }

    // scheduledQueueObject 설정하는 메서드 추가
    public void setScheduledQueueObject(QueueObject scheduledQueueObject) {
        this.scheduledQueueObject = scheduledQueueObject;
    }

    @Scheduled(fixedDelay = 1000) // 1초마다 반복
    public void reserveTicket() {
        if (scheduledQueueObject == null) {
            log.info("==== scheduledQueueObject NULL ====");
            return;
        }

        // ticketInfo의 정보 가져오기
        TicketInfo ticketInfo = ticketInfoRepository.findById(scheduledQueueObject.getTicketInfoId())
                .orElseThrow(() -> new TicketInfoNotFoundException("티켓 정보를 찾을 수 없습니다."));

        // 해당 티켓 정보에 속한 대기열의 크기 가져오기
        Long queueSize = redisTemplate.opsForZSet().zCard(ticketInfo.getTicketInfoId().toString());

        if (queueSize >= ticketInfo.getStock()) {
            log.info("==== 티켓이 매진되었습니다. {}, {} ====", ticketInfo.getStock(), queueSize);
            return;
        }

        log.info("scheduledQueueObject : {}", scheduledQueueObject); // TODO: 삭제 예정

        publish(scheduledQueueObject);
        getOrder(scheduledQueueObject);

        // 처리가 끝나면 scheduledQueueObject 초기화
        scheduledQueueObject = null;
    }

    // 대기자 생성
    public void getOrder(QueueObject queueObject){
        // Redis Sorted Set에서 가져올 범위 설정
        final long start = FIRST_ELEMENT;
        final long end = LAST_ELEMENT;

        // RedisQueue에 등록된 Key
        String key = queueObject.getTicketInfoId().toString();
        // Redis Sorted Set에서 범위 내의 멤버들을 가져옴
        Set<Object> queue = redisTemplate.opsForZSet().range(key, start, end);

        // 대기열 상황
        for (Object data : queue) {
            Long rank = redisTemplate.opsForZSet().rank(key, data);
            log.info("'{}'님의 현재 대기열은 {}명 남았습니다.", data, rank);
        }
    }

    // ticket 발급
    public void publish(QueueObject queueObject) {
        // Redis Sorted Set에서 가져올 범위 설정
        final long start = FIRST_ELEMENT;
        final long end = PUBLISH_SIZE - 1;

        // RedisQueue에 등록된 Key
        String key = queueObject.getTicketInfoId().toString();
        // Redis Sorted Set에서 범위 내의 멤버들을 가져옴
        Set<Object> queue = redisTemplate.opsForZSet().range(key, start, end);

        // 발급 시작
        for (Object data : queue) {
            // JSON 데이터를 추출
            ObjectMapper objectMapper = new ObjectMapper();
            QueueObject restoredQueueObject = objectMapper.convertValue(data, QueueObject.class);

            // 기존 데이터는 제거
            redisTemplate.opsForZSet().remove(key, data);

            // 티켓 발급을 위한 TicketRequestDto 생성
            TicketRequestDto ticketRequestDto = TicketRequestDto.builder()
                    .ticketInfoId(restoredQueueObject.getTicketInfoId())
                    .posX(restoredQueueObject.getPosX())
                    .posY(restoredQueueObject.getPosY())
                    .build();

            // 티켓 발급을 위한 User 조회
//            User user = userRepository.findById(restoredQueueObject.getUserId())
//                    .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
            User user = User.builder().userId(restoredQueueObject.getUserId()).build();

            // 티켓 발급
            ticketService.reserveTicket(user, ticketRequestDto);

            log.info("'{}'님의 {}번 티켓이 발급되었습니다 (좌석 : {}, {})",
                    user.getUsername(),
                    restoredQueueObject.getTicketInfoId(),
                    restoredQueueObject.getPosX(),
                    restoredQueueObject.getPosY());
        }
    }
}
