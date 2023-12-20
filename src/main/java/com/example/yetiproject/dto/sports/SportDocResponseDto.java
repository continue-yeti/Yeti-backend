package com.example.yetiproject.dto.sports;

import java.time.LocalDateTime;

import com.example.yetiproject.document.SportDoc;

import lombok.Data;

@Data
public class SportDocResponseDto {

	private Long sportId;
	private String stadiumName;
	private String matchDate;
	private String sportName;

	public SportDocResponseDto(SportDoc sportDoc) {
		this.sportId = sportDoc.getSportId();
		this.stadiumName = sportDoc.getStadiumName();
		this.matchDate = sportDoc.getMatchDate();
		this.sportName = sportDoc.getSportName();
	}
}
