package com.example.yetiproject.repository;

import static com.example.yetiproject.config.CacheConfig.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.yetiproject.config.CacheConfig;
import com.example.yetiproject.entity.Ticket;
import com.example.yetiproject.entity.TicketInfo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketInfoRepository extends JpaRepository<TicketInfo, Long> {
	@Override //기본 적으로 findById 을 제공하기 때문에 Override 하여 재정의 후 사용
	@EntityGraph(attributePaths = {"sports","sports.stadium"})
	Optional<TicketInfo> findById(Long ticketId);
	@Cacheable(value = CACHE_STOCK, key = "'stock:' + #ticketInfoId")
	@Query(value="select stock from ticket_info where ticket_info_id = ?", nativeQuery = true)
	String getStockforTicketInfo(Long ticketInfoId);

	@Query(value="select open_date, close_date from ticket_info where ticket_info_id = ?", nativeQuery = true)
	List<Timestamp[]> getOpenDateCloseDateforTicketInfo(Long ticketInfoId);

	@Cacheable(value = CacheConfig.CACHE_DATE, key = "'openDate:' + #ticketInfoId")
	@Query(value="select open_date from ticket_info where ticket_info_id = ?", nativeQuery = true)
	Timestamp getOpenDateTicketInfo(Long ticketInfoId);

	@Cacheable(value = CacheConfig.CACHE_DATE, key = "'closeDate' + #ticketInfoId")
	@Query(value="select close_date from ticket_info where ticket_info_id = ?", nativeQuery = true)
	Timestamp getCloseDateTicketInfo(Long ticketInfoId);
}
