package com.example.yetiproject.controller;

import org.apache.coyote.Response;
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

	@GetMapping("")
	public ResponseEntity viewListOfReservedTickets(Long userId){
		return new ResponseEntity(ticketService.getUserTicketList(userId), HttpStatus.OK);
	}

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
