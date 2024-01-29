package com.example.yetiproject.repository;

import com.example.yetiproject.config.CacheConfig;
import com.example.yetiproject.entity.TicketInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
//@CacheConfig(cacheNames = "ticketInfo")
public interface TicketInfoRepository extends JpaRepository<TicketInfo, Long> {
	@Override
	@EntityGraph(attributePaths = {"sports","sports.stadium"})
	@Cacheable(value = "ticketInfo", key = "'ticketInfo'+#ticketInfoId", unless = "#result == null")
	Optional<TicketInfo> findById(Long ticketInfoId);

	@Cacheable(value = CacheConfig.CACHE_STOCK, key = "'ticketInfo:' + #ticketInfoId", unless = "#result == null")
	@Query(value="select stock from ticket_info where ticket_info_id = ?", nativeQuery = true)
	String getStockforTicketInfo(Long ticketInfoId);

	@Query(value="select open_date, close_date from ticket_info where ticket_info_id = ?", nativeQuery = true)
	List<Timestamp[]> getOpenDateCloseDateforTicketInfo(Long ticketInfoId);
	//cache:date:openDate:1 cache:date:openDate:null
	@Cacheable(value = CacheConfig.CACHE_DATE, key = "'openDate:' + #ticketInfoId", unless = "#result == null")
	@Query(value="select open_date from ticket_info where ticket_info_id = ?", nativeQuery = true)
	Timestamp getOpenDateTicketInfo(Long ticketInfoId);

	@Cacheable(value = CacheConfig.CACHE_DATE, key = "'closeDate' + #ticketInfoId", unless = "#result == null")
	@Query(value="select close_date from ticket_info where ticket_info_id = ?", nativeQuery = true)
	Timestamp getCloseDateTicketInfo(Long ticketInfoId);
}
