package com.example.yetiproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.yetiproject.service.TicketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TicketController {
	private final TicketService ticketService;
	@GetMapping
	public ResponseEntity viewListOfReservedTickets(Long userId){
		return new ResponseEntity(ticketService.getTicketList(userId), HttpStatus.OK);
	}

}
