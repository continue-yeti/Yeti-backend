package com.example.yetiproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.yetiproject.entity.Stadium;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {
}
