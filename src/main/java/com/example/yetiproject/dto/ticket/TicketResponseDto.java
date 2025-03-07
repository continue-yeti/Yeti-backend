package com.example.yetiproject.dto.ticket;

import com.example.yetiproject.dto.ticketinfo.TicketInfoResponseDto;
import com.example.yetiproject.entity.Ticket;
import com.example.yetiproject.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDto {
	Long ticketId;
	String seat;
	Long userId;

	public TicketResponseDto(Ticket ticket) {
		this.ticketId = ticket.getTicketId();
		this.seat = ticket.getSeat();
		this.userId = ticket.getUserId();
	}
}
