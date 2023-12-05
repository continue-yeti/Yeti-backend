package com.example.yetiproject.dto.ticketinfo;

import com.example.yetiproject.entity.TicketInfo;
import lombok.Getter;

import java.util.Date;

@Getter
public class TicketInfoResponseDto {
    private Long id;
    private Date openDate;
    private Date closeDate;
    private Long ticketPrice;
    private Long stock;
    private SportsResponseDto sports;


    public TicketInfoResponseDto(TicketInfo ticketInfo) {
        this.id = ticketInfo.getTicketInfoId();
        this.openDate = ticketInfo.getOpenDate();
        this.closeDate = ticketInfo.getCloseDate();
        this.ticketPrice = ticketInfo.getTicketPrice();
        this.stock = ticketInfo.getStock();
        this.sports = new SportsResponseDto(ticketInfo.getSports());
    }
}
