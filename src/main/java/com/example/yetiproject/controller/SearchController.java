package com.example.yetiproject.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.yetiproject.service.SearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

	private final SearchService searchService;

	@GetMapping("")
	public Map<String, Object> searchStringQuery(
		@RequestParam("queryText") String queryText, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
		return searchService.searchNativeQuery(queryText, pageNumber, pageSize);
	}
}
