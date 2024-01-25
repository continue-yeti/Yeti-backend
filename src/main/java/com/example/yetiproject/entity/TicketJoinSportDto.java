package com.example.yetiproject.entity;

import lombok.Getter;

public interface TicketJoinSportDto {
	Long getUserId();
	String getSeat();
	String getSportName();
	String getMatchDate();

}
