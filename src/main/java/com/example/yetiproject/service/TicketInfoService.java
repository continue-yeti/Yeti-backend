package com.example.yetiproject.service;

import com.example.yetiproject.dto.ticketinfo.TicketInfoRequestDto;
import com.example.yetiproject.dto.ticketinfo.TicketInfoResponseDto;
import com.example.yetiproject.entity.TicketInfo;
import com.example.yetiproject.repository.TicketInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketInfoService {

    private final TicketInfoRepository ticketInfoRepository;


    // 티켓 정보 생성
    public TicketInfoResponseDto createTicketInfo(TicketInfoRequestDto ticketRequestDto) {
        TicketInfo ticketInfo = new TicketInfo(ticketRequestDto);
        ticketInfoRepository.save(ticketInfo);
        return new TicketInfoResponseDto(ticketInfo);
    }

    // 티켓 정보 조회
    public TicketInfoResponseDto getTicketInfo(Long id) {
        TicketInfo ticketInfo = findTicketInfo(id);
        return new TicketInfoResponseDto(ticketInfo);
    }

    // 티켓 정보 수정
    @Transactional
    public TicketInfoResponseDto updateTicketInfo(Long id, TicketInfoRequestDto requestDto) {
        TicketInfo ticketInfo = findTicketInfo(id);
        ticketInfo.update(requestDto);
        return new TicketInfoResponseDto(ticketInfo);
    }

    // 티켓 정보 삭제
    public String deleteTicketInfo(Long id) {
        TicketInfo ticketInfo = findTicketInfo(id);
        ticketInfoRepository.delete(ticketInfo);
        return  "티켓 정보를 삭제하였습니다.";
    }

    // 티켓 정보 찾기
    private TicketInfo findTicketInfo(Long id) {
        TicketInfo ticketInfo = ticketInfoRepository.findById(id).orElseThrow();
        return ticketInfo;
    }
}
