package com.example.yetiproject.entity;

import com.example.yetiproject.dto.ticket.TicketRequestDto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "tickets")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long ticketId;

	@Column(name="seat")
	String seat;

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "user_id")
	// private User user;
	@Column(name = "user_id", nullable = false)
	Long userId;

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "ticketInfo_id")
	// private TicketInfo ticketInfo;
	@Column(name = "ticketInfo_id", nullable = false)
	Long ticketInfoId;

	public Ticket(Long userId, TicketRequestDto ticketRequestDto) {
		this.seat = ticketRequestDto.getSeat();
		this.userId = userId;
		this.ticketInfoId = ticketRequestDto.getTicketInfoId();
	}
}
