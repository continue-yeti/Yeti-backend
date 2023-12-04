package com.example.yetiproject.controller;

import com.example.yetiproject.dto.ticketinfo.TicketInfoRequestDto;
import com.example.yetiproject.dto.ticketinfo.TicketInfoResponseDto;
import com.example.yetiproject.service.TicketInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketInfoController {

    private final TicketInfoService ticketInfoService;


    @PostMapping("")
    public ResponseEntity<TicketInfoResponseDto> createTicketInfo(@RequestBody TicketInfoRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketInfoService.createTicketInfo(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketInfoResponseDto> getTicketInfo(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(ticketInfoService.getTicketInfo(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketInfoResponseDto> updateTicketInfo(@PathVariable Long id, @RequestBody TicketInfoRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(ticketInfoService.updateTicketInfo(id, requestDto));
    }

}
