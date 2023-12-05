package com.example.yetiproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.yetiproject.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	@Query(value = "select b from Bookmark b where b.ticketInfo.ticketInfoId = :ticketInfoId and b.user.userId = :userId")
	Optional<Bookmark> findByTicketInfoIdAndUserIdJpql(@Param("ticketInfoId") Long ticketInfoId, @Param("userId") Long userId);
}
