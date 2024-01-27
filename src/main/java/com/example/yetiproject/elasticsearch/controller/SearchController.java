// package com.example.yetiproject.elasticsearch.controller;
//
// import java.util.Map;
//
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
//
// import com.example.yetiproject.elasticsearch.service.SearchService;
//
// import lombok.RequiredArgsConstructor;
//
// @RestController
// @RequiredArgsConstructor
// @RequestMapping("/api/search")
// public class SearchController {
//
// 	private final SearchService searchService;
//
// 	@GetMapping("")
// 	public Map<String, Object> searchStringQuery(
// 		@RequestParam("queryText") String queryText, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
// 		return searchService.searchNativeQuery(queryText, pageNumber, pageSize);
// 	}
//
// 	@GetMapping("/rdbms")
// 	public Map<String, Object> searchQuery(
// 		@RequestParam("queryText") String queryText, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
// 		return searchService.searchQuery(queryText, pageNumber, pageSize);
// 	}
// }
