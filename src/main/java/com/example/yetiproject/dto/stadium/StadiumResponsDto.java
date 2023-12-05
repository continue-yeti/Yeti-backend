package com.example.yetiproject.dto.stadium;

import com.example.yetiproject.entity.Stadium;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StadiumResponsDto {

    private Long id;
    private String stadiumName;

    public StadiumResponsDto(Stadium stadium) {
        this.id = stadium.getId();
        this.stadiumName = stadium.getStadiumName();


    }
}
