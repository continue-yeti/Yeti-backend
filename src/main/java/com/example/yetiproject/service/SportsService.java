package com.example.yetiproject.service;

import com.example.yetiproject.dto.sports.SportsRequestDto;
import com.example.yetiproject.dto.sports.SportsResponseDto;
import com.example.yetiproject.entity.Sports;
import com.example.yetiproject.entity.Stadium;
import com.example.yetiproject.exception.entity.sports.SportsNotFoundException;
import com.example.yetiproject.exception.entity.stadium.StadiumNotFoundException;
import com.example.yetiproject.repository.SportsRepository;
import com.example.yetiproject.repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j(topic = "SportsService")
@Service
@RequiredArgsConstructor
public class SportsService {
    private final SportsRepository sportsRepository;
    private final StadiumRepository stadiumRepository;

    public String createSports(SportsRequestDto sportsRequestDto) {
        Stadium stadium = findById(sportsRequestDto.getStadiumId());
        Sports sports = new Sports(sportsRequestDto, stadium);
        sportsRepository.save(sports);

        return "경기가 등록되었습니다";

    }
    public List<SportsResponseDto> getSports() {
        return sportsRepository.findAll().stream().map(SportsResponseDto::new).collect(Collectors.toList());
    }

    public SportsResponseDto getSportsInfo(Long sportId) {
        Sports sports = findSports(sportId);
        return new SportsResponseDto(sports);
    }

    public String updateSport(Long sportId, SportsRequestDto sportsRequestDto) {
        Sports sports = sportsRepository.findById(sportId)
                .orElseThrow(() -> new SportsNotFoundException("존재하지 않는 경기입니다"));
        Long stadiumId = sportsRequestDto.getStadiumId();
        if (stadiumId != null) {
            Stadium newStadium = stadiumRepository.findById(stadiumId)
                    .orElseThrow(() -> new StadiumNotFoundException("New Stadium not found"));

            sports.setStadium(newStadium);
        }
        sports.update(sportsRequestDto);
        sportsRepository.save(sports);
        return "경기 내용이 변경되었습니다";
    }

    public String deleteSport(Long sportId) {
        Sports sports = findSports(sportId);
        sportsRepository.delete(sports);
        return "경기가 삭제되었습니다";
    }

    private Stadium findById(Long stadiumId) {
        return stadiumRepository.findById(stadiumId).orElseThrow(() -> new StadiumNotFoundException("존재하지 않는 경기장 입니다"));
    }
    private Sports findSports(Long sportId) {
        return sportsRepository.findById(sportId).orElseThrow(() -> new SportsNotFoundException("존재하지 않는 경기입니다"));
    }
}
