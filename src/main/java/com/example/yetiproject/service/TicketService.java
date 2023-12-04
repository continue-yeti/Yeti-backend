package com.example.yetiproject.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.yetiproject.entity.Ticket;
import com.example.yetiproject.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
	private final TicketRepository ticketRepository;
	public List<Ticket> getTicketList(Long userId) {
		return ticketRepository.findUserTicketList(userId);
	}
}
