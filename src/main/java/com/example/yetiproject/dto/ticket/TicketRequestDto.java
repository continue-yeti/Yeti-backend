package com.example.yetiproject.dto.ticket;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDto {
	private Long userId;
	private Long now;
	private Long ticketInfoId;
	private Long posX;
	private Long posY;

	public TicketRequestDto(Long userId, Long ticketInfoId, Long posX, Long posY) {
		this.userId = userId;
		this.ticketInfoId = ticketInfoId;
		this.posX = posX;
		this.posY = posY;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
