package com.example.yetiproject.controller;

import com.example.yetiproject.dto.stadium.StadiumRequestDto;
import com.example.yetiproject.dto.stadium.StadiumResponsDto;
import com.example.yetiproject.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StadiumController {

    public final StadiumService stadiumService;


    @PostMapping("/stadium")
    public ResponseEntity<StadiumResponsDto> createStadium(@RequestBody StadiumRequestDto stadiumRequestDto) {
        return new ResponseEntity<>(stadiumService.createStadium(stadiumRequestDto), HttpStatus.OK);
    }
}
