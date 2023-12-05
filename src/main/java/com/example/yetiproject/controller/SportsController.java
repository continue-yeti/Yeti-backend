package com.example.yetiproject.controller;

import com.example.yetiproject.dto.ApiResponse;
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
    public ApiResponse createSports(@RequestBody SportsRequestDto sportsRequestDto) {
        return ApiResponse.success("Sports 생성에 성공했습니다.", sportsService.createSports(sportsRequestDto));
    }

    @GetMapping("/sports")
    public ApiResponse<List<SportsResponseDto>> getSports() {
        return ApiResponse.success("Sports 리스트 조회에 성공했습니다.", sportsService.getSports());
    }

    @GetMapping("/sports/{sportId}")
    public ApiResponse<SportsResponseDto> getSportsInfo(@PathVariable Long sportId) {
        return ApiResponse.success("Sports 상세 조회에 성공했습니다.", sportsService.getSportsInfo(sportId));
    }

    @PutMapping("/sports/{sportId}")
    public ApiResponse<SportsResponseDto> updateSport(@PathVariable Long sportId, @RequestBody SportsRequestDto sportsRequestDto) {
        return ApiResponse.success("Sports 수정에 성공했습니다.", sportsService.updateSport(sportId, sportsRequestDto));
    }

    @DeleteMapping("/sports/{sportId}")
    public ApiResponse deleteSport(@PathVariable Long sportId) {
        return ApiResponse.successWithNoContent(sportsService.deleteSport(sportId));
    }

}
