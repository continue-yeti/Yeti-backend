package com.example.yetiproject.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
	// queue-already-register
	QUEUE_ALREADY_REGISTERED_USER(HttpStatus.CONFLICT, "not possible", "Already registered in queue");
	private final HttpStatus httpStatus;
	private final String code;
	private final String reason;

	public ApplicationException build(){
		return new ApplicationException(httpStatus, code, reason);
	}

}
