package com.example.yetiproject.repository;

import com.example.yetiproject.entity.Sports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportsRepository extends JpaRepository<Sports, Long> {
}
