package com.example.yetiproject.service;

import com.example.yetiproject.dto.ticketinfo.TicketInfoRequestDto;
import com.example.yetiproject.dto.ticketinfo.TicketInfoResponseDto;
import com.example.yetiproject.entity.TicketInfo;
import com.example.yetiproject.repository.TicketInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


}
