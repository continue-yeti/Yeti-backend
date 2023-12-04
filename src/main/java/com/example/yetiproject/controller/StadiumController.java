package com.example.yetiproject.controller;

import com.example.yetiproject.dto.stadium.StadiumCreateRequestDto;
import com.example.yetiproject.dto.stadium.StadiumModifyRequestDto;
import com.example.yetiproject.dto.stadium.StadiumResponseDto;
import com.example.yetiproject.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StadiumController {

	private final StadiumService stadiumService;

	// 경기장 생성
	@PostMapping("/stadiums")
	public ResponseEntity<StadiumResponseDto> createStadium(@RequestBody StadiumCreateRequestDto requestDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(stadiumService.createStadium(requestDto));
	}

	// 경기장 조회
	@GetMapping("/stadiums/{stadiumId}")
	public ResponseEntity<StadiumResponseDto> getStadium(@PathVariable Long stadiumId) {
		return ResponseEntity.ok().body(stadiumService.getStadium(stadiumId));
	}

	// 경기장 수정
	@PutMapping("/stadiums/{stadiumId}")
	public ResponseEntity<String> updateStadium(@PathVariable Long stadiumId, @RequestBody StadiumModifyRequestDto requestDto) {
		stadiumService.updateStadium(stadiumId, requestDto);
		return ResponseEntity.ok().body("경기장 수정이 완료되었습니다.");
	}

	// 경기장 삭제
	@DeleteMapping("/stadiums/{stadiumId}")
	public ResponseEntity<String> deleteStadium(@PathVariable Long stadiumId) {
		stadiumService.deleteStadium(stadiumId);
		return ResponseEntity.ok().body("경기장 삭제가 완료되었습니다.");
	}
}
