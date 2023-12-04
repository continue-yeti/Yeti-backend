package com.example.yetiproject.dto.ticketinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class TicketInfoRequestDto {
    private Long ticketPrice;
    private Long stock;
    private Date openDate;
    private Date closeDate;
}
