package com.example.yetiproject.controller;

import com.example.yetiproject.dto.ApiResponse;
import com.example.yetiproject.dto.stadium.StadiumCreateRequestDto;
import com.example.yetiproject.dto.stadium.StadiumModifyRequestDto;
import com.example.yetiproject.dto.stadium.StadiumResponseDto;
import com.example.yetiproject.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StadiumController {

	private final StadiumService stadiumService;

	// 경기장 생성
	@PostMapping("/stadiums")
	public ApiResponse<StadiumResponseDto> createStadium(@RequestBody StadiumCreateRequestDto requestDto) {
		return ApiResponse.success("경기장 생성에 성공했습니다.", stadiumService.createStadium(requestDto));
	}

	// 경기장 조회
	@GetMapping("/stadiums/{stadiumId}")
	public ApiResponse<StadiumResponseDto> getStadium(@PathVariable(name = "stadiumId") Long stadiumId) {
		return ApiResponse.success("경기장 조회에 성공했습니다.", stadiumService.getStadium(stadiumId));
	}

	// 경기장 수정
	@PutMapping("/stadiums/{stadiumId}")
	public ApiResponse<StadiumResponseDto> updateStadium(@PathVariable(name = "stadiumId") Long stadiumId, @RequestBody StadiumModifyRequestDto requestDto) {
		return ApiResponse.success("경기장 수정에 성공했습니다.", stadiumService.updateStadium(stadiumId, requestDto));

	}

	// 경기장 삭제
	@DeleteMapping("/stadiums/{stadiumId}")
	public ApiResponse deleteStadium(@PathVariable(name = "stadiumId") Long stadiumId) {
		return ApiResponse.successWithNoContent(stadiumService.deleteStadium(stadiumId));
	}
}
