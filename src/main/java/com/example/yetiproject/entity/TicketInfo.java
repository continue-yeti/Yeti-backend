package com.example.yetiproject.entity;

import java.util.Date;

import com.example.yetiproject.dto.ticketinfo.TicketInfoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "ticket_info")
public class TicketInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketInfoId;

    @Column(nullable = false)
    private Long ticketPrice;

    @Column(nullable = false)
    private Long stock;

    @Column(nullable = false)
    private Date openDate;

    @Column(nullable = false)
    private Date closeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sports_id")
    private Sports sports;

    public TicketInfo(TicketInfoRequestDto ticketRequestDto) {
        this.ticketPrice = ticketRequestDto.getTicketPrice();
        this.stock = ticketRequestDto.getStock();
        this.openDate = ticketRequestDto.getOpenDate();
        this.closeDate = ticketRequestDto.getCloseDate();
    }

    public void update(TicketInfoRequestDto requestDto) {
        this.ticketPrice = requestDto.getTicketPrice();
        this.stock = requestDto.getStock();
        this.openDate = requestDto.getOpenDate();
        this.closeDate = requestDto.getCloseDate();
    }
}
