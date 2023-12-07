package com.example.yetiproject.dto.stadium;

import com.example.yetiproject.entity.Stadium;

import lombok.Getter;

@Getter
public class StadiumResponseDto {

	private Long stadiumId;
	private String stadiumName;

	public StadiumResponseDto(Stadium stadium) {
		this.stadiumId = stadium.getStadiumId();
		this.stadiumName = stadium.getStadiumName();
	}
}
