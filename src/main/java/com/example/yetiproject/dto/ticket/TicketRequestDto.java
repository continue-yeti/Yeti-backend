package com.example.yetiproject.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketRequestDto {
	private Long userId;
	private Long ticketInfoId;
	private Long posX;
	private Long posY;

}
