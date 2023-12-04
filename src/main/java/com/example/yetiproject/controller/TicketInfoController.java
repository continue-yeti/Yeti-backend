package com.example.yetiproject.controller;

import com.example.yetiproject.dto.ticketinfo.TicketInfoRequestDto;
import com.example.yetiproject.dto.ticketinfo.TicketInfoResponseDto;
import com.example.yetiproject.service.TicketInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketInfoController {

    private final TicketInfoService ticketInfoService;


    @PostMapping("")
    public ResponseEntity<TicketInfoResponseDto> createTicketInfo(@RequestBody TicketInfoRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketInfoService.createTicketInfo(requestDto));
    }

}
