package com.example.yetiproject.service;

import com.example.yetiproject.dto.stadium.StadiumRequestDto;
import com.example.yetiproject.dto.stadium.StadiumResponsDto;
import com.example.yetiproject.entity.Stadium;
import com.example.yetiproject.repository.StadiumRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StadiumService {

    private final StadiumRepository stadiumRepository;

    @Transactional
    public StadiumResponsDto createStadium(StadiumRequestDto stadiumRequestDto) {

        Stadium stadium = new Stadium(stadiumRequestDto);
        return new StadiumResponsDto(stadiumRepository.save(stadium));
    }
}
