package com.example.yetiproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.yetiproject.entity.Ticket;
import com.example.yetiproject.entity.TicketJoinSportDto;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	@Query(value="select * from tickets where user_id = ? ", nativeQuery = true)
	List<Ticket> findUserTicketList(Long userId);

	@Query(value="select * from tickets where user_id = ? and ticket_id = ? ", nativeQuery = true)
	Optional<Ticket> findUserTicket(Long userId, Long ticketId);

	@Query(value="select * from tickets where ticket_info_id = ? and posx = ? and posy = ?", nativeQuery = true)
	Optional<Ticket> findByTicketPosition(Long ticketInfoId,Long posX, Long posY);

	@Query(value="select t.user_id, t.seat, s.sport_name, s.match_date from tickets as t, sports as s where ticket_info_id = ? and t.seat = ?", nativeQuery = true)
	List<TicketJoinSportDto> ticketJoinSport(Long ticketInfoId, String seat);
}
