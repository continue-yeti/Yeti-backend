package com.example.yetiproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.dto.ticket.TicketResponseDto;
import com.example.yetiproject.entity.Sports;
import com.example.yetiproject.entity.Stadium;
import com.example.yetiproject.entity.Ticket;
import com.example.yetiproject.entity.TicketInfo;
import com.example.yetiproject.entity.User;
import com.example.yetiproject.facade.repository.RedisRepository;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.example.yetiproject.repository.TicketJdbcBatchRepository;
import com.example.yetiproject.repository.TicketRepository;
import com.example.yetiproject.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
	@InjectMocks
	private TicketService ticketService;
	@Mock
	private TicketRepository ticketRepository;
	@Mock
	private RedisRepository redisRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private TicketJdbcBatchRepository ticketJdbcBatchRepository;
	@Mock
	private TicketInfoRepository ticketInfoRepository;

	private static TicketRequestDto ticketRequestDto;

	@BeforeAll
	static void beforeAll() {
		ticketRequestDto = TicketRequestDto.builder()
			.userId(1L)
			.ticketInfoId(1L)
			.seat(String.valueOf(1) + 'A' + 12)
			.build();
	}

	@Test
	@DisplayName("예매하기")
	void test() {
		//given
		Long ticketId = 2L;
		User user = User.builder().userId(1L).build();
		TicketInfo ticketInfo = TicketInfo.builder().ticketInfoId(1L).build();
		given(userRepository.findById(ticketRequestDto.getUserId())).willReturn(Optional.of(user));
		given(ticketInfoRepository.findById(ticketRequestDto.getTicketInfoId())).willReturn(Optional.of(ticketInfo));

		Ticket ticket = new Ticket(user.getUserId(), ticketRequestDto);
		//when
		TicketResponseDto result = ticketService.reserveTicket(user.getUserId(), ticketRequestDto);

		//then
		// verify 메서드를 사용해 특정 메서드가 호출되었는지 확인할 수 있어
		verify(userRepository, times(1)).findById(user.getUserId());
		verify(ticketInfoRepository, times(1)).findById(ticketInfo.getTicketInfoId());
		verify(ticketRepository, times(1)).save(any(Ticket.class));

		System.out.println("result ticketId = " + result.getTicketId());
		assertNotNull(result);
		assertEquals(result.getTicketId(), 2L);
	}

	// @Test
	// @DisplayName("예매 취소")
	// void test2() {
	// 	//given
	// 	Long ticketId = 2L;
	// 	User user = User.builder().userId(1L).build();
	// 	Ticket ticket = Ticket.builder().seat(String.valueOf(1 + 'A' + 12))
	// 		.build();
	// 	given(ticketRepository.findById(ticketId)).willReturn(Optional.of(ticket));
	//
	// 	//when
	// 	ResponseEntity msg = ticketService.(user, ticketId);
	//
	// 	//then
	// 	verify(ticketRepository, times(1)).findById(ticketId);
	// 	assertEquals("<200 OK OK,해당 티켓을 취소하였습니다.,[]>", msg.toString());
	//
	// }

	@Test
	@DisplayName("예매 insert")
	void insertTicket() {
		long startTime = System.currentTimeMillis();
		User user = User.builder().userId(1L).build();
		Stadium stadium = Stadium.builder().stadiumId(1L).build();
		Sports sports = Sports.builder().sportId(1L).stadium(stadium).build();
		TicketInfo ticketInfo = TicketInfo.builder().ticketInfoId(1L).sports(sports).build();

		given(ticketInfoRepository.findById(ticketRequestDto.getTicketInfoId())).willReturn(Optional.of(ticketInfo));
		given(redisRepository.increase(anyString())).willReturn(1L);

		// when
		for (int i = 0; i < 100000; i++) {
			ticketService.reserveTicketSortedSet(user.getUserId(), ticketRequestDto);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("TicketRepository 저장 속도 = " + (endTime - startTime));
	}

	@Test
	@DisplayName("예매 bulkInsert")
	void bulkInsertTicket() throws JsonProcessingException {
		long startTime = System.currentTimeMillis();
		Stadium stadium = Stadium.builder().stadiumId(1L).build();
		Sports sports = Sports.builder().sportId(1L).stadium(stadium).build();
		TicketInfo ticketInfo = TicketInfo.builder().ticketInfoId(1L).stock(5L).sports(sports).build();

		given(redisRepository.get("ticketInfo:1:stock")).willReturn("0");
		given(ticketInfoRepository.findById(ticketRequestDto.getTicketInfoId())).willReturn(Optional.of(ticketInfo));

		// when
		List<TicketRequestDto> ticketRequestDtoList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			ticketRequestDtoList.add(ticketRequestDto);
		}

		ticketService.reserveTicketsInBatch(ticketRequestDtoList);

		long endTime = System.currentTimeMillis();
		System.out.println("TicketRepository 저장 속도 = " + (endTime - startTime));
	}
}
