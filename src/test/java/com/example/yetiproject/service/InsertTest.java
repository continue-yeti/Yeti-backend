package com.example.yetiproject.service;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.entity.User;
import com.example.yetiproject.facade.sortedset.WaitingQueueSortedSetService;
import com.example.yetiproject.repository.TicketRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
//@WithMockUser // JWT 인증 된 가짜 객체 유저
public class InsertTest {

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    WaitingQueueSortedSetService waitingQueueSortedSetService;

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketRepository ticketRepository;

    /**
     * 테스트 진행하려면 redis에 ticketInfo:1:stock 생성 후 진행
     * set ticketInfo:1:stock 0
     */

    @Test
    @DisplayName("insert")
    public void test1() {
        long startTime = System.currentTimeMillis();

        User user = User.builder().userId(1L).build();
        for (int i = 0; i < 10000; i++) {
            TicketRequestDto ticketRequestDto = TicketRequestDto.builder()
                    .ticketInfoId(1L)
                    .posX((long) i)
                    .posY((long) i).build();
            ticketService.reserveTicketSortedSet(user.getUserId(), ticketRequestDto);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("TicketRepository 저장 속도 = " + (endTime - startTime));
    } // Cache O : 10000개 1분 23초, 2분 1초
      // Cache X : 10000개 2분 21초, 3분 43초

    @Test
    @DisplayName("bulk insert")
    public void test2() throws JsonProcessingException {
        long startTime = System.currentTimeMillis();

        User user = User.builder().userId(1L).build();
        List<TicketRequestDto> ticketRequestDtoList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            TicketRequestDto ticketRequestDto = TicketRequestDto.builder()
                    .ticketInfoId(1L)
                    .posX((long) i)
                    .posY((long) i).build();
            ticketRequestDtoList.add(ticketRequestDto);
        }
        ticketService.reserveTicketsInBatch(ticketRequestDtoList);

        long endTime = System.currentTimeMillis();
        System.out.println("TicketRepository 저장 속도 = " + (endTime - startTime));
    } // Cache O : 10000개 24초, 33초
      // Cache X : 10000개 1분 9초, 1분 26초
}
