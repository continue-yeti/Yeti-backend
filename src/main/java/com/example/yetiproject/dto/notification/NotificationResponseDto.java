package com.example.yetiproject.dto.notification;

import org.aspectj.weaver.ast.Not;

import com.example.yetiproject.entity.Notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponseDto {

	private Long notificationId;
	private String content;
	private String url;

	public NotificationResponseDto(Notification notification) {
		this.notificationId = notification.getNotificationId();
		this.content = notification.getContent();
		this.url = notification.getUrl();
	}
}
