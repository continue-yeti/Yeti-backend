package com.example.yetiproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.yetiproject.dto.ticket.TicketRequestDto;
import com.example.yetiproject.dto.ticket.TicketResponseDto;
import com.example.yetiproject.entity.Ticket;
import com.example.yetiproject.entity.TicketInfo;
import com.example.yetiproject.entity.User;
import com.example.yetiproject.exception.entity.Ticket.TicketCancelException;
import com.example.yetiproject.exception.entity.Ticket.TicketNotFoundException;
import com.example.yetiproject.facade.repository.RedisRepository;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.example.yetiproject.repository.TicketJdbcBatchRepository;
import com.example.yetiproject.repository.TicketRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "TicketService")
@Service
@RequiredArgsConstructor
public class TicketService {
	private final TicketRepository ticketRepository;
	private final TicketInfoRepository ticketInfoRepository;
	private final RedisRepository redisRepository;
	private final TicketJdbcBatchRepository ticketJdbcBatchRepository;

	private final String TICKETINFO_STOCK_COUNT = "ticketInfo:%s:stock";

	public List<TicketResponseDto> getUserTicketList(User user) {
		return ticketRepository.findUserTicketList(user.getUserId()).stream().map(TicketResponseDto::new).toList();
	}
	public TicketResponseDto showDetailTicket(Long userId, Long ticketId) {
		return new TicketResponseDto(ticketRepository.findUserTicket(userId, ticketId).
				orElseThrow(() -> new TicketNotFoundException("잘못된 티켓 조회입니다.")));
	}

	@Transactional
	public TicketResponseDto reserveTicket(Long userId, TicketRequestDto ticketRequestDto) {
		TicketInfo ticketInfo = ticketInfoRepository.findById(ticketRequestDto.getTicketInfoId()).get();
		Ticket ticket = new Ticket(userId, ticketRequestDto);
		ticketRepository.save(ticket);

		ticketInfo.updateStock(-1L);
		return new TicketResponseDto(ticket);
	}

	@Transactional
	public TicketResponseDto reserveTicketQueue(Long userId, TicketRequestDto ticketRequestDto) {
		Ticket ticket = new Ticket(userId, ticketRequestDto);
		ticketRepository.save(ticket);
		log.info("{} 좌석 티켓 발급에 성공하였습니다.", ticketRequestDto.getSeat());
		return new TicketResponseDto(ticket);
		//return new TicketCreateResponseDto(ticketRepository.ticketJoinSport(ticketRequestDto.getTicketInfoId(), ticketRequestDto.getSeat()).get(0));
	}

	@Transactional
	public String reserveTicketSortedSet(Long userId, TicketRequestDto ticketRequestDto) {
		TicketInfo ticketInfo = ticketInfoRepository.findById(ticketRequestDto.getTicketInfoId()).get();
		log.info("ticketInfo : {}", ticketInfo.getTicketInfoId());
		log.info("redisRepository.get(TICKETINFO_STOCK_COUNT.formatted(ticketInfo.getTicketInfoId()) : {}", redisRepository.get(TICKETINFO_STOCK_COUNT.formatted(ticketInfo.getTicketInfoId())));

		if (Integer.parseInt(redisRepository.get(TICKETINFO_STOCK_COUNT.formatted(ticketInfo.getTicketInfoId()))) ==
			Integer.parseInt(ticketInfoRepository.getStockforTicketInfo(ticketInfo.getTicketInfoId()))
		) {
			log.info("[ticketInfo : " + ticketRequestDto.getTicketInfoId() + " 은 매진입니다.]");
			return "매진";
		}
		Ticket ticket = new Ticket(userId, ticketRequestDto);
		increase(ticketRequestDto.getTicketInfoId()); // ticket 예매 수 증가
		ticketRepository.save(ticket); // ticket 저장
		//		return new TicketResponseDto(ticket);
		return "예매 완료";
	}

	@Transactional
	public void reserveTicketsInBatch(List<TicketRequestDto> ticketRequestDtoList) {
		List<Ticket> tickets = new ArrayList<>();

		// 티켓 일괄 발급 처리
		for (TicketRequestDto ticketRequestDto : ticketRequestDtoList) {
			TicketInfo ticketInfo = ticketInfoRepository.findById(ticketRequestDto.getTicketInfoId()).get();
			Ticket ticket = new Ticket(ticketRequestDto.getUserId(), ticketRequestDto);

			// 매진 확인
			if (Integer.parseInt(redisRepository.get(TICKETINFO_STOCK_COUNT.formatted(ticketInfo.getTicketInfoId()))) ==
				Integer.parseInt(ticketInfoRepository.getStockforTicketInfo(ticketRequestDto.getTicketInfoId()))
			) {
				log.info("[ticketInfo : " + ticketRequestDto.getTicketInfoId() + " 은 매진입니다.]");
				return;
			}

			tickets.add(ticket);
			increase(ticketRequestDto.getTicketInfoId()); // ticket 예매 수 증가
		}

		// 티켓 한번에 저장
		ticketJdbcBatchRepository.batchUpdate(tickets);
	}

	@Transactional
	public ResponseEntity cancelUserTicket(User user, Long ticketId) {
		Ticket ticket = ticketRepository.findUserTicket(user.getUserId(), ticketId)
			.orElseThrow(() -> new TicketNotFoundException("해당된 티켓정보가 없습니다."));
		TicketInfo ticketInfo = ticketInfoRepository.findById(ticket.getTicketInfoId())
			.orElseThrow(()-> new IllegalArgumentException("조회되지 않는 경기정보입니다."));

		// 티켓을 산 유저와 접근한 유저가 같은지 확인
		if (!Objects.equals(user.getUserId(), ticket.getUserId())) {
			throw new RuntimeException("회원님이 구매하신 티켓이 아닙니다.");
		}

		try {
			ticketInfo.updateStock(1L);
			ticketRepository.delete(ticket);
		}catch (Exception e){
			throw new TicketCancelException("예매를 취소 할 수 없습니다.");
		}
		return ResponseEntity.ok().body("해당 티켓을 취소하였습니다.");
	}

	public Long increase(Long ticketInfoId) {
		return redisRepository.increase(TICKETINFO_STOCK_COUNT.formatted(ticketInfoId));
	}

}
