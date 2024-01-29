package com.example.yetiproject.controller;


import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor
// @Controller
// public class SocketController {
// 	private final WaitingQueueListService waitingQueueListService;
//
// 	@MessageMapping("/reserve/queue/list")
// 	@SendTo("/topic/{userId}")
// 	public RegisterUserResponse reserveTicketQueueList(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody TicketRequestDto ticketRequestDto) throws
// 		JsonProcessingException {
// 		return new RegisterUserResponse(
// 			waitingQueueListService.registerQueue(userDetails.getUser().getUserId(), ticketRequestDto)
// 		);
// 	}
// }
