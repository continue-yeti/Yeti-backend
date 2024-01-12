package com.example.yetiproject.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.yetiproject.entity.Bookmark;
import com.example.yetiproject.entity.Notification;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	// 북마크 추가, 제거
	@Query(value = "select b from Bookmark b where b.ticketInfo.ticketInfoId = :ticketInfoId and b.user.userId = :userId")
	Optional<Bookmark> findByTicketInfoIdAndUserIdJpql(@Param("ticketInfoId") Long ticketInfoId, @Param("userId") Long userId);

	// 알람
	@Query(value = "select b from Bookmark b where b.ticketInfo.openDate between :start and :end")
	List<Bookmark> findByOpenDateJpql(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
