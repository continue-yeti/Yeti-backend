package com.example.yetiproject.entity;

import com.example.yetiproject.dto.bookmark.BookmarkRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Bookmark {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookmarkId;

	@ManyToOne
	@JoinColumn(name = "ticket_info_id")
	private TicketInfo ticketInfo;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;


	public Bookmark(TicketInfo ticketInfo, User user) {
		this.ticketInfo = ticketInfo;
		this.user = user;
	}
}
