package com.example.yetiproject.controller;

import com.example.yetiproject.dto.sports.SportsRequestDto;
import com.example.yetiproject.dto.sports.SportsResponseDto;
import com.example.yetiproject.service.SportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SportsController {

    private final SportsService sportsService;

    @PostMapping("/sports")
    public ResponseEntity<String> createSports(@RequestBody SportsRequestDto sportsRequestDto) {
        String successMessage = sportsService.createSports(sportsRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE + ";charset=" + StandardCharsets.UTF_8)
                .body(successMessage);
    }

    @GetMapping("/sports")
    public List<SportsResponseDto> getSports() {
        return sportsService.getSports();
    }

    @GetMapping("/sports/{sportId}")
    public SportsResponseDto getSportsInfo(@PathVariable Long sportId) {
        return sportsService.getSportsInfo(sportId);
    }

    @PutMapping("/sports/{sportId}")
    public String updateSport(@PathVariable Long sportId, @RequestBody SportsRequestDto sportsRequestDto) {
        return sportsService.updateSport(sportId, sportsRequestDto);
    }

    @DeleteMapping("/sports/{sportId}")
    public String deleteSport(@PathVariable Long sportId) {
        return sportsService.deleteSport(sportId);
    }

}
