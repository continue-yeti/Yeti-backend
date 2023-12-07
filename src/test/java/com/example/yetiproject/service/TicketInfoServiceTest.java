package com.example.yetiproject.service;

import com.example.yetiproject.dto.ticketinfo.TicketInfoRequestDto;
import com.example.yetiproject.dto.ticketinfo.TicketInfoResponseDto;
import com.example.yetiproject.entity.Sports;
import com.example.yetiproject.entity.Stadium;
import com.example.yetiproject.repository.SportsRepository;
import com.example.yetiproject.repository.TicketInfoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketInfoServiceTest {

    @Mock
    private SportsRepository sportsRepository;

    @Mock
    private TicketInfoRepository ticketInfoRepository;

    @InjectMocks
    TicketInfoService ticketInfoService;

    static TicketInfoRequestDto requestDto;

    @BeforeAll
    static void beforeAll() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date openDate = dateFormat.parse("2023-12-25");
            Date closeDate = dateFormat.parse("2023-12-26");

            requestDto = TicketInfoRequestDto.builder()
                    .sportsId(1L)
                    .openDate(openDate)
                    .closeDate(closeDate)
                    .ticketPrice(10000L)
                    .stock(100L)
                    .build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("티켓정보 생성")
    void test1() {
        // given
        Stadium stadium = new Stadium();
        Sports sports = Sports.builder().sportId(1L).stadium(stadium).build();
        when(sportsRepository.findById(requestDto.getSportsId())).thenReturn(Optional.of(sports));
//        given(sportsRepository.findById(requestDto.getSportsId())).willReturn(Optional.of(sports));
        System.out.println(sports.getSportId());

        // when
        TicketInfoService ticketInfoService = new TicketInfoService(ticketInfoRepository, sportsRepository);
        TicketInfoResponseDto result = ticketInfoService.createTicketInfo(requestDto);

        // then
        System.out.println(result);
        assertEquals(result.getId(), 1L);
    }
}
