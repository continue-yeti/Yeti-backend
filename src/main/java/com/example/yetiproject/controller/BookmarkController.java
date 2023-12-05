package com.example.yetiproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.yetiproject.service.BookmarkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sports")
public class BookmarkController {

	private final BookmarkService bookmarkService;

	@PostMapping("/bookmark")
	public ResponseEntity<String> bookmark(@RequestParam Long ticketInfoId, @RequestParam Long userId) {
		bookmarkService.bookmark(ticketInfoId, userId);
		return ResponseEntity.status(HttpStatus.CREATED).body("찜하기 완료");
	}
}
