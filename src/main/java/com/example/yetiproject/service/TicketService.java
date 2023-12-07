package com.example.yetiproject.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.dto.ticket.TicketResponseDto;
import com.example.yetiproject.entity.Ticket;
import com.example.yetiproject.entity.TicketInfo;
import com.example.yetiproject.entity.User;
import com.example.yetiproject.exception.entity.Ticket.TicketNotFoundException;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.example.yetiproject.repository.TicketRepository;
import com.example.yetiproject.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
	private final TicketRepository ticketRepository;
	private final UserRepository userRepository;
	private final TicketInfoRepository ticketInfoRepository;
	public List<TicketResponseDto> getUserTicketList() {
		return ticketRepository.findUserTicketList().stream().map(TicketResponseDto::new).toList();
	}
	public TicketResponseDto showDetailTicket(Long userId, Long ticketId) {
		return new TicketResponseDto(ticketRepository.findUserShowDetailTicket(userId, ticketId));
	}

	public TicketResponseDto reserveTicket(Long ticketId, TicketRequestDto ticketRequestDto) {
		User user = userRepository.findById(ticketRequestDto.getUserId()).get();
		TicketInfo ticketInfo = ticketInfoRepository.findById(ticketRequestDto.getTicketInfoId()).get();
		Ticket ticket = new Ticket(ticketId, user, ticketInfo, ticketRequestDto);
		try {
			ticketRepository.save(ticket);
		}catch (Exception e){
			throw new IllegalArgumentException("예약을 할 수 없습니다.");
		}
		return new TicketResponseDto(ticket);
	}

	@Transactional
	public ResponseEntity cancelUserTicket(Long ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId)
			.orElseThrow(() -> new TicketNotFoundException("해당된 티켓정보가 없습니다."));
		try {
			ticketRepository.delete(ticket);
		}catch (Exception e){
			new IllegalArgumentException("예매를 취소 할 수 없습니다.");
		}
		return ResponseEntity.ok().body("해당 티켓을 취소하였습니다.");
	}
}
