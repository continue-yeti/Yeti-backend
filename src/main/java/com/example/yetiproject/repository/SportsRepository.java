package com.example.yetiproject.repository;

import com.example.yetiproject.entity.Sports;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportsRepository extends JpaRepository<Sports, Long> {
	Page<Sports> findBySportNameContainingOrStadium_StadiumNameContainingOrderByMatchDateDesc(String sportName, String stadiumName, Pageable pageable);
}
