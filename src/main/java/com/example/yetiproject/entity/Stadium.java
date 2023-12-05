package com.example.yetiproject.entity;

import com.example.yetiproject.dto.stadium.StadiumRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String stadiumName;

    public Stadium(StadiumRequestDto stadiumRequestDto) {

        this.stadiumName = stadiumRequestDto.getStadiumName();
    }
}
