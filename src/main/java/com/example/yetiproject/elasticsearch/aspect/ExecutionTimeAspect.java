package com.example.yetiproject.elasticsearch.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

	@Around("execution(* com.example.yetiproject.controller.*.*(..))")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object proceed = joinPoint.proceed();
		long endTime = System.currentTimeMillis();
		System.out.println(joinPoint.getSignature() + " executed in " + (endTime - startTime) + "ms");
		return proceed;
	}
}