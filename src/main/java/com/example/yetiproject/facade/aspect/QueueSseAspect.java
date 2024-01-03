// package com.example.yetiproject.facade.aspect;
//
// import java.util.Map;
//
// import org.aspectj.lang.annotation.AfterReturning;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Before;
// import org.aspectj.lang.annotation.Pointcut;
// import org.springframework.stereotype.Component;
// import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
// import com.example.yetiproject.auth.security.UserDetailsImpl;
// import com.example.yetiproject.dto.ticket.TicketRequestDto;
// import com.example.yetiproject.facade.WaitingQueueSortedSetService;
// import com.example.yetiproject.facade.repository.RedisRepository;
// import com.example.yetiproject.facade.service.QueueSseService;
// import com.example.yetiproject.repository.EmitterRepository;
//
// import lombok.RequiredArgsConstructor;
//
// @Aspect
// @Component
// @RequiredArgsConstructor
// public class QueueSseAspect {
//
// 	private final QueueSseService queueSseService;
// 	private final WaitingQueueSortedSetService waitingQueueSortedSetService;
// 	private final EmitterRepository emitterRepository;
// 	private final RedisRepository redisRepository;
// 	private final String QUEUE_KEY = "ticket";
//
// 	@Pointcut(value = "execution(* com.example.yetiproject.controller.TicketController.reserveTicketQueueSortedSet(..)) && args(userDetails, ticketRequestDto)", argNames = "userDetails,ticketRequestDto")
// 	public void reserveQueueExecution(UserDetailsImpl userDetails, TicketRequestDto ticketRequestDto) {}
//
// 	@AfterReturning(value = "reserveQueueExecution(userDetails, ticketRequestDto)", argNames = "userDetails,ticketRequestDto")
// 	public void checkQueue(UserDetailsImpl userDetails, TicketRequestDto ticketRequestDto) {
// 		if (redisRepository.zSize(QUEUE_KEY) < 100) {
// 			String userId = String.valueOf(userDetails.getUser().getUserId());
// 			Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(userId);
// 			emitters.values().forEach(SseEmitter::complete);
// 			emitterRepository.deleteAllEmitterStartWithId(userId);
// 		}
// 	}
// }
//
