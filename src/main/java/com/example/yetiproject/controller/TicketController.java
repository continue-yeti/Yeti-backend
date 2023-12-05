package com.example.yetiproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.service.TicketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mytickets")
public class TicketController {
	private final TicketService ticketService;

	// TODO. user jwt 토큰 받기 필요
	// 예매한 티켓 목록 조회
	@GetMapping("")
	public ResponseEntity viewListOfReservedTickets(){
		return new ResponseEntity(ticketService.getUserTicketList(), HttpStatus.OK);
	}

	// TODO. user jwt 토큰 받기 필요
	// 예매한 티켓 상세 조회
	@GetMapping("/userId/{userId}/ticketId/{ticketId}")
	public ResponseEntity detailViewReservedTicket(@PathVariable(name = "userId") Long userId, @PathVariable(name = "ticketId") Long ticketId){
		System.out.println("[Controller : detailViewReservedTicket userId = ] " + userId);
		return new ResponseEntity(ticketService.showDetailTicket(userId, ticketId), HttpStatus.OK);
	}

	// TODO. user jwt 토큰 받기 필요
	// 예매 하기
	@PostMapping("/reserve/{ticketId}")
	public ResponseEntity reserveTicket(@PathVariable(name = "ticketId") Long ticketId, @RequestBody TicketRequestDto ticketRequestDto){
		log.info("TicketController ticket_id = {}", ticketId);
		return new ResponseEntity(ticketService.reserveTicket(ticketId, ticketRequestDto), HttpStatus.OK);
	}

	// TODO. user jwt 토큰 받기 필요
	// 예매 취소
	@DeleteMapping("/{ticketId}")
	public ResponseEntity cancelTicket(@PathVariable(name = "ticketId") Long ticketId){
		return ticketService.cancelUserTicket(ticketId);
	}

}
