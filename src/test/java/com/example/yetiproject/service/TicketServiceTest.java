package com.example.yetiproject.service;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.dto.ticket.TicketResponseDto;
import com.example.yetiproject.entity.*;
import com.example.yetiproject.facade.repository.RedisRepository;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.example.yetiproject.repository.TicketJdbcBatchRepository;
import com.example.yetiproject.repository.TicketRepository;
import com.example.yetiproject.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @InjectMocks
    private TicketService ticketService;
    @Mock
    private TicketInfoRepository ticketInfoRepository;
    @Mock
    private RedisRepository redisRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private TicketJdbcBatchRepository ticketJdbcBatchRepository;

    private static TicketRequestDto ticketRequestDto;

    @BeforeAll
    static void beforeAll() {
        ticketRequestDto = TicketRequestDto.builder()
                .ticketInfoId(1L)
                .posX(12L)
                .posY(14L)
                .build();
    }

    @Test
    @DisplayName("예매하기")
    void test() {
        //given
        User user = User.builder().userId(1L).build();
        Stadium stadium = Stadium.builder().stadiumId(1L).build();
        Sports sports = Sports.builder().sportId(1L).stadium(stadium).build();
        TicketInfo ticketInfo = TicketInfo.builder().ticketInfoId(1L).sports(sports).build();
        given(ticketInfoRepository.findById(ticketRequestDto.getTicketInfoId())).willReturn(Optional.of(ticketInfo));
        given(redisRepository.increase(anyString())).willReturn(1L);

        //when
        String result = ticketService.reserveTicketSortedSet(user.getUserId(), ticketRequestDto);

        //then
        System.out.println("result : " + result);
        assertNotNull(result);
        assertEquals(result, "예매 완료");
    }


//    @Test
//    @DisplayName("예매 취소")
//    void test2() {
//        //given
//        Long ticketId = 2L;
//        Ticket ticket = Ticket.builder().posY(12L).build();
//        given(ticketRepository.findById(ticketId)).willReturn(Optional.of(ticket));
//
//        //when
//        ResponseEntity msg = ticketService.cancelUserTicket(ticketId);
//
//        //then
//        verify(ticketRepository, times(1)).findById(ticketId);
//        assertEquals("<200 OK OK,해당 티켓을 취소하였습니다.,[]>", msg.toString());
//
//    }

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
