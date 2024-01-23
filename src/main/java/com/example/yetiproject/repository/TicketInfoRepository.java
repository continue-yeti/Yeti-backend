package com.example.yetiproject.repository;

import com.example.yetiproject.entity.TicketInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "ticketInfo")
public interface TicketInfoRepository extends JpaRepository<TicketInfo, Long> {
	@Override //기본 적으로 findById 을 제공하기 때문에 Override 하여 재정의 후 사용
	@EntityGraph(attributePaths = {"sports","sports.stadium"})
	@Cacheable(key = "#ticketInfoId", unless = "#result == null")
	Optional<TicketInfo> findById(Long ticketInfoId);

	@Query(value="select stock from ticket_info where ticket_info_id = ?", nativeQuery = true)
	@Cacheable(key = "#ticketInfo + '_stock'", unless = "#result == null")
	Long getStockforTicketInfo(Long ticketInfo);

}
