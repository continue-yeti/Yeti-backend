package com.example.yetiproject.dto.sports;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SportsRequestDto {
    private Long stadiumId;
    private String sportName;
    private String matchDate;
}
