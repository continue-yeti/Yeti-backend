package com.example.yetiproject.entity;

import com.example.yetiproject.dto.ticket.TicketRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "tickets")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long ticketId;

	@Column(name="posX")
	Long posX;
	@Column(name="posY")
	Long posY;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "ticketInfo_id")
	private TicketInfo ticketInfo;

	public Ticket(Long ticketId, TicketRequestDto ticketRequestDto) {
		this.ticketId = ticketId;
		this.posX = ticketRequestDto.getPosX();
		this.posY = ticketRequestDto.getPosY();
		this.user.setUserId(ticketRequestDto.getUserId());
		this.ticketInfo.setTicketInfoId(ticketRequestDto.getTicketInfoId());
	}
}
