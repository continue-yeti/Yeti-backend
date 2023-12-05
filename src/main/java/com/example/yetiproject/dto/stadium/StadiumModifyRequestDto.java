package com.example.yetiproject.dto.stadium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StadiumModifyRequestDto {
	private String stadiumName;
}
