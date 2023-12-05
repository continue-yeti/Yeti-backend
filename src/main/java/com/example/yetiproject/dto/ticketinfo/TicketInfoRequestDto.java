package com.example.yetiproject.dto.ticketinfo;

import com.example.yetiproject.entity.Stadium;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class TicketInfoRequestDto {
    private Long sportsId;
    private Long ticketPrice;
    private Long stock;
    private Date openDate;
    private Date closeDate;
}
