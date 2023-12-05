package com.example.yetiproject.service;

import com.example.yetiproject.dto.stadium.StadiumCreateRequestDto;
import com.example.yetiproject.dto.stadium.StadiumModifyRequestDto;
import com.example.yetiproject.dto.stadium.StadiumResponseDto;
import com.example.yetiproject.entity.Stadium;
import com.example.yetiproject.repository.StadiumRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StadiumService {

	private final StadiumRepository stadiumRepository;

	// 경기장 생성
	public StadiumResponseDto createStadium(StadiumCreateRequestDto requestDto) {
		Stadium stadium = new Stadium(requestDto);
		stadiumRepository.save(stadium);
		return new StadiumResponseDto(stadium);
	}


	// 경기장 조회
	public StadiumResponseDto getStadium(Long stadiumId) {
		Stadium stadium = stadiumRepository.findById(stadiumId).orElseThrow(
			() -> new EntityNotFoundException("존재하지 않는 경기장입니다."));
		return new StadiumResponseDto(stadium);
	}


	// 경기장 수정
	@Transactional
	public void updateStadium(Long stadiumId, StadiumModifyRequestDto requestDto) {
		Stadium stadium = stadiumRepository.findById(stadiumId).orElseThrow(
			() -> new EntityNotFoundException("존재하지 않는 경기장입니다."));

		stadium.update(requestDto);
	}

	// 경기장 삭제
	public void deleteStadium(Long stadiumId) {
		Stadium stadium = stadiumRepository.findById(stadiumId).orElseThrow(
			() -> new EntityNotFoundException("존재하지 않는 경기장입니다."));

		stadiumRepository.delete(stadium);
	}
}
