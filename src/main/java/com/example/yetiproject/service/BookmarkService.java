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

	private final NotificationService notificationService;
	private final BookmarkRepository bookmarkRepository;
	private final TicketInfoRepository ticketInfoRepository;
	private final UserRepository userRepository;

	public String bookmark(Long ticketInfoId, Long userId) {
		TicketInfo ticketInfo = ticketInfoRepository.findById(ticketInfoId).orElseThrow(
			() -> new EntityNotFoundException("티켓 정보가 없습니다."));

		User user = userRepository.findById(userId).orElseThrow(
			() -> new EntityNotFoundException("유저 정보가 없습니다."));

		// 북마크 검색
		Bookmark bookmark = bookmarkRepository.findByTicketInfoIdAndUserIdJpql(ticketInfoId, userId).orElse(null);

		// 존재하면 삭제, 존재하지 않으면 생성
		if (Objects.isNull(bookmark)) {
			bookmarkRepository.save(new Bookmark(ticketInfo, user));
			return "찜하기 완료";
		} else {
			bookmarkRepository.delete(bookmark);
			return "찜하기 해제";
		}
	}

	@Transactional
	public void notificationToUser(Long bookmarkId, Long userId) {
		Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElseThrow(
			() -> new EntityNotFoundException("찜 내역이 없습니다."));

		User user = userRepository.findById(userId).orElseThrow(
			() -> new EntityNotFoundException("회원정보가 없습니다."));

		String sportName = bookmark.getTicketInfo().getSports().getSportName();
		String date = bookmark.getTicketInfo().getSports().getMatchDate();
		String content = "곧 " + date + "일자 " + sportName + "경기의 티켓 예매가 시작됩니다!";
		notificationService.send(content, user);
	}
}
