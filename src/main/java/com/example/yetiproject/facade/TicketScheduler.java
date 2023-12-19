//package com.example.yetiproject.facade;
//
//import com.example.yetiproject.entity.TicketInfo;
//import com.example.yetiproject.exception.entity.TicketInfo.TicketInfoNotFoundException;
//import com.example.yetiproject.repository.TicketInfoRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class TicketScheduler {
//
//    private final TicketInfoRepository ticketInfoRepository;
//    private final RedisTemplate<String, Object> redisTemplate;
//    private final WaitingQueueService waitingQueueService;
//
//    @Scheduled(fixedDelay = 1000) // 1초마다 반복
//    public void reserveTicket() {
////        if (waitingQueueService.scheduledQueueObject == null) {
////            log.info("==== scheduledQueueObject NULL ====");
////            return;
////        }
////
////        // ticketInfo의 정보 가져오기
////        TicketInfo ticketInfo = ticketInfoRepository.findById(scheduledQueueObject.getTicketInfoId())
////                .orElseThrow(() -> new TicketInfoNotFoundException("티켓 정보를 찾을 수 없습니다."));
////
////        // 해당 티켓 정보에 속한 대기열의 크기 가져오기
////        Long queueSize = redisTemplate.opsForZSet().zCard(ticketInfo.getTicketInfoId().toString());
////
////        if (queueSize >= ticketInfo.getStock()) {
////            log.info("==== 티켓이 매진되었습니다. {}, {} ====", ticketInfo.getStock(), queueSize);
////            return;
////        }
////
////        log.info("scheduledQueueObject : {}", scheduledQueueObject); // TODO: 삭제 예정
////
////        waitingQueueService.publish(scheduledQueueObject);
////        waitingQueueService.getOrder(scheduledQueueObject);
////
////        // 처리가 끝나면 scheduledQueueObject 초기화
////        scheduledQueueObject = null;
//    }
//}
