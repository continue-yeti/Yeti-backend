package com.example.yetiproject.dto.ticket;

import com.example.yetiproject.entity.TicketJoinSportDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketCreateResponseDto {
	TicketJoinSportDto ticketJoinSportDto;
}
