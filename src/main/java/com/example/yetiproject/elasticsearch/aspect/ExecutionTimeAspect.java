// package com.example.yetiproject.elasticsearch.aspect;
//
// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.springframework.stereotype.Component;
//
// import lombok.extern.slf4j.Slf4j;
//
// @Aspect
// @Component
// @Slf4j(topic = "Search 시간 비교")
// public class ExecutionTimeAspect {
//
// 	@Around("execution(* com.example.yetiproject.elasticsearch.controller.*.*(..))")
// 	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
// 		long startTime = System.currentTimeMillis();
// 		Object proceed = joinPoint.proceed();
// 		long endTime = System.currentTimeMillis();
// 		log.info(joinPoint.getSignature() + " executed in " + (endTime - startTime) + "ms");
// 		return proceed;
// 	}
// }