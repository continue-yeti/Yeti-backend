package com.example.yetiproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.yetiproject.entity.Notification;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
