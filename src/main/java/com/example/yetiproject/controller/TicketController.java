package com.example.yetiproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.service.TicketService;

import lombok.RequiredArgsConstructor;

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
	@GetMapping("/userId/{userId}/ticketId/{ticketId}")
	public ResponseEntity detailViewReservedTicket(Long userId, Long ticketId){
		return new ResponseEntity(ticketService.showDetailTicket(userId, ticketId), HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity reserveTicket(@RequestParam Long userId, @RequestParam Long ticketId, @RequestBody TicketRequestDto ticketRequestDto){
		return new ResponseEntity(ticketService.reserveTicket(userId, ticketId, ticketRequestDto), HttpStatus.OK);
	}

	@DeleteMapping("/{ticketId}")
	public ResponseEntity cancelTicket(Long ticketId){
		return ticketService.cancelUserTicket(ticketId);
	}

}
