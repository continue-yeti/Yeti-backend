package com.example.yetiproject.dto.sports;

import com.example.yetiproject.entity.Sports;
import com.example.yetiproject.entity.Stadium;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SportsResponseDto {
    private Long id;
    private String sportName;
    private String matchDate;
    private Long stadiumId;
    private String stadiumName;

    public SportsResponseDto(Sports sports) {
        this.id = sports.getId();
        this.sportName = sports.getSportName();
        this.matchDate = sports.getMatchDate();
        this.stadiumId = sports.getStadium().getId();
        this.stadiumName = sports.getStadium().getStadiumName();

    }
}
