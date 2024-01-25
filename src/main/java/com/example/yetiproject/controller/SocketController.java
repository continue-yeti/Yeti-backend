package com.example.yetiproject.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.yetiproject.auth.security.UserDetailsImpl;
import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.dto.user.RegisterUserResponse;
import com.example.yetiproject.facade.WaitingQueueListBulkService;
import com.example.yetiproject.facade.sortedset.WaitingQueueListService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SocketController {
	private final WaitingQueueListService waitingQueueListService;

	@MessageMapping("/reserve/queue/list")
	@SendTo("/topic/{userId}")
	public RegisterUserResponse reserveTicketQueueList(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody TicketRequestDto ticketRequestDto) throws
		JsonProcessingException {
		return new RegisterUserResponse(
			waitingQueueListService.registerQueue(userDetails.getUser().getUserId(), ticketRequestDto)
		);
	}
}
