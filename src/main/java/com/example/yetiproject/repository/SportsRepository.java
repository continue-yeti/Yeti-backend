package com.example.yetiproject.repository;

import java.util.List;
import java.util.Optional;

import com.example.yetiproject.entity.Bookmark;
import com.example.yetiproject.entity.Sports;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SportsRepository extends JpaRepository<Sports, Long> {
	Page<Sports> findBySportNameContainingOrStadium_StadiumNameContainingOrderByMatchDateDesc(String sportName, String stadiumName, Pageable pageable);
}
