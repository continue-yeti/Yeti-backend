package com.example.yetiproject.facade;

import com.example.yetiproject.auth.security.UserDetailsImpl;
import com.example.yetiproject.dto.ticket.TicketRequestDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Data // redis에서 사용할 Serializable 인터페이스를 구현
@NoArgsConstructor
public class QueueObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private Long ticketInfoId;
    private Long posX;
    private Long posY;

    public QueueObject(UserDetailsImpl userDetails, TicketRequestDto ticketRequestDto) {
        this.userId = userDetails.getUser().getUserId();
        this.ticketInfoId = ticketRequestDto.getTicketInfoId();
        this.posX = ticketRequestDto.getPosX();
        this.posY = ticketRequestDto.getPosY();
    }
}
