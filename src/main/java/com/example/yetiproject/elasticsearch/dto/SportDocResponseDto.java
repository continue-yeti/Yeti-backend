package com.example.yetiproject.elasticsearch.dto;

import com.example.yetiproject.dto.sports.SportsResponseDto;
import com.example.yetiproject.elasticsearch.document.SportDoc;

import com.example.yetiproject.entity.Sports;
import lombok.Data;

@Data
public class SportDocResponseDto {

	private SportsResponseDto sport;
	private String stadiumName;
	private String matchDate;
	private String sportName;

	public SportDocResponseDto(SportDoc sportDoc, Sports sports) {
		this.sport = new SportsResponseDto(sports);
		this.stadiumName = sportDoc.getStadiumName();
		this.matchDate = sportDoc.getMatchDate();
		this.sportName = sportDoc.getSportName();
	}
}
