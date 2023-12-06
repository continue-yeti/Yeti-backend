package com.example.yetiproject.dto.ticketinfo;

import com.example.yetiproject.entity.Stadium;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketInfoRequestDto {
    private Long sportsId;
    private Long ticketPrice;
    private Long stock;
    private Date openDate;
    private Date closeDate;
}
