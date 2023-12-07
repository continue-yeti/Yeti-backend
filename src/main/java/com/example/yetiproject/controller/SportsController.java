package com.example.yetiproject.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.yetiproject.dto.ApiResponse;
import com.example.yetiproject.dto.sports.SportsRequestDto;
import com.example.yetiproject.dto.sports.SportsResponseDto;
import com.example.yetiproject.service.SportsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SportsController {

    private final SportsService sportsService;

    @PostMapping("/sports")
    public ApiResponse createSports(@RequestBody SportsRequestDto sportsRequestDto) {
        return ApiResponse.success("Sports 생성에 성공했습니다.", sportsService.createSports(sportsRequestDto));
    }

    @GetMapping("/sports")
    public ApiResponse<List<SportsResponseDto>> getSports() {
        return ApiResponse.success("Sports 리스트 조회에 성공했습니다.", sportsService.getSports());
    }

    @GetMapping("/sports/{sportId}")
    public ApiResponse<SportsResponseDto> getSportsInfo(@PathVariable(name = "sportId") Long sportId) {
        return ApiResponse.success("Sports 상세 조회에 성공했습니다.", sportsService.getSportsInfo(sportId));
    }

    @PutMapping("/sports/{sportId}")
    public ApiResponse<SportsResponseDto> updateSport(@PathVariable(name = "sportId") Long sportId, @RequestBody SportsRequestDto sportsRequestDto) {
        return ApiResponse.success("Sports 수정에 성공했습니다.", sportsService.updateSport(sportId, sportsRequestDto));
    }

    @DeleteMapping("/sports/{sportId}")
    public ApiResponse deleteSport(@PathVariable(name = "sportId") Long sportId) {
        return ApiResponse.successWithNoContent(sportsService.deleteSport(sportId));
    }

}
