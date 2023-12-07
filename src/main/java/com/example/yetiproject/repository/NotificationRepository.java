package com.example.yetiproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.yetiproject.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
