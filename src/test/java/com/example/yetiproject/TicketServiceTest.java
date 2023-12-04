package com.example.yetiproject;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.dto.ticket.TicketResponseDto;
import com.example.yetiproject.entity.Ticket;
import com.example.yetiproject.service.TicketService;

@SpringBootTest
public class TicketServiceTest {
	@Autowired
	TicketService ticketService;

	@Test
	@DisplayName("유저 예매내역 조회")
	void test(){
		Long userId = 1L;
		List<TicketResponseDto> ticketResponseDto = ticketService.getUserTicketList(userId);
		System.out.println("ticket Id = " + ticketResponseDto.get(0).getTicketId());
		Assertions.assertEquals(ticketResponseDto.get(0).getTicketId(), 1L);
	}

	@Test
	@DisplayName("티켓 상세예매내역 조회")
	void test1(){
		Long userId = 1L;
		Long ticketId = 1L;
		Ticket ticket = ticketService.showDetailTicket(userId, ticketId);
		Assertions.assertEquals(ticket.getUser().getUsername(), "jungmin");
	}

}
