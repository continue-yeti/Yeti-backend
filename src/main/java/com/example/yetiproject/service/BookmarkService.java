package com.example.yetiproject.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.yetiproject.entity.Bookmark;
import com.example.yetiproject.entity.TicketInfo;
import com.example.yetiproject.entity.User;
import com.example.yetiproject.repository.BookmarkRepository;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.example.yetiproject.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final TicketInfoRepository ticketInfoRepository;
	private final UserRepository userRepository;

	public void bookmark(Long ticketInfoId, Long userId) {
		TicketInfo ticketInfo = ticketInfoRepository.findById(ticketInfoId).orElseThrow(
			() -> new EntityNotFoundException("티켓 정보가 없습니다."));

		User user = userRepository.findById(userId).orElseThrow(
			() -> new EntityNotFoundException("유저 정보가 없습니다."));

		// 북마크 검색
		Bookmark bookmark = bookmarkRepository.findByTicketInfoIdAndUserIdJpql(ticketInfoId, userId).orElse(null);

		// 존재하면 삭제, 존재하지 않으면 생성
		if (Objects.isNull(bookmark)) {
			bookmarkRepository.save(new Bookmark(ticketInfo, user));
		} else {
			bookmarkRepository.delete(bookmark);
		}
	}
}
