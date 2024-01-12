package com.example.yetiproject.auth;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.yetiproject.entity.Notification;
import com.example.yetiproject.entity.User;
import com.example.yetiproject.repository.EmitterRepository;
import com.example.yetiproject.repository.EmitterRepositoryImpl;
import com.example.yetiproject.repository.NotificationRepository;
import com.example.yetiproject.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "로그아웃 성공 감지 핸들러")
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

	private final UserRepository userRepository;
	private final NotificationRepository notificationRepository;

	@Autowired
	private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
			() -> new EntityNotFoundException("유저 정보를 찾을 수 없습니다."));

		String userId = user.getUserId().toString();
		Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(userId);
		emitters.forEach((id, emitter) -> emitter.complete());
		Map<String, Object> eventCache = emitterRepository.findAllEventCacheStartWithByUserId(userId);
		eventCache.forEach((id, event) -> notificationRepository.delete((Notification)event));
		emitterRepository.deleteAllEmitterStartWithId(userId);
		emitterRepository.deleteAllEventCacheStartWithId(userId);
	}
}
