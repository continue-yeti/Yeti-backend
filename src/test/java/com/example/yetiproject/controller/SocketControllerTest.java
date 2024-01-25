package com.example.yetiproject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@ActiveProfiles("local")
public class SocketControllerTest {

	final String TARGET_URI = "http://localhost:8080/connect";
	final String SENDMESSAGE_URI = "/app/api/mytickets/reserve/queue/list";
	WebSocketStompClient stompClient;

	private List<Transport> createTransportClient(){
		List<Transport> transports = new ArrayList<>();
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		return transports;
	}

	@BeforeEach
	public void setup() throws InterruptedException{
		stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));

		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
	}

	@Test
	public void contextLoad() throws ExecutionException, InterruptedException, TimeoutException {

		WebSocketHttpHeaders httpHeaders = new WebSocketHttpHeaders();
		httpHeaders.add("Authorization" , "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdW5nbWluIiwiZXhwIjoxNzA2MTc4Mjc0LCJpYXQiOjE3MDYwOTE4NzR9.F5geTs1CKQUP6FfnEeIQT7pOO3DZFdKOcoFOGwt5kT4");
		httpHeaders.add("Content-Type", "application/json");
		StompHeaders stompHeaders = new StompHeaders();
		StompSession stompSession = stompClient.connect(TARGET_URI, httpHeaders, stompHeaders, new StompSessionHandlerAdapter() {
		}).get(1, TimeUnit.SECONDS);

		// Send
		stompSession.send(SENDMESSAGE_URI, "test");

	}
}
