package com.example.yetiproject.elasticsearch.dto;

import com.example.yetiproject.elasticsearch.document.SportDoc;

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
