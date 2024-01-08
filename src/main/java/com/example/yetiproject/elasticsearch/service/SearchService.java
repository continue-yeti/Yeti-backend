package com.example.yetiproject.elasticsearch.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.yetiproject.entity.Ticket;
import com.example.yetiproject.exception.entity.sports.SportsNotFoundException;
import com.example.yetiproject.repository.TicketInfoRepository;
import com.example.yetiproject.repository.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.yetiproject.dto.sports.SportsResponseDto;
import com.example.yetiproject.elasticsearch.document.SportDoc;
import com.example.yetiproject.elasticsearch.dto.SportDocResponseDto;
import com.example.yetiproject.entity.Sports;
import com.example.yetiproject.repository.SportsRepository;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

	private final ElasticsearchOperations elasticsearchOperations;
	private final SportsRepository sportsRepository;
	private final TicketRepository ticketRepository;

	public Map<String, Object> searchNativeQuery(String queryText, int pageNumber, int pageSize) {

		// NativeQuery Build
		Query query = NativeQuery.builder()
			.withQuery(QueryBuilders.multiMatch()
				.query(queryText)
				.fields("sport_name", "stadium_name")
				.type(TextQueryType.MostFields)
				.fuzziness("AUTO")
				.analyzer("nori")
				.build()._toQuery())
			.withPageable(PageRequest.of(pageNumber, pageSize))
			.withSort(Sort.by(Sort.Order.desc("_score"), Sort.Order.desc("match_date")))
			.build();

		return search(query);
	}

	private Map<String, Object> search(Query query) {

		// ElasticsearchOperations 검색
		SearchHits<SportDoc> searchHits = elasticsearchOperations.search(query, SportDoc.class);

		// 리턴받을 Map 객체
		Map<String, Object> result = new HashMap<>();

		// 검색된 문서 갯수
		result.put("count", searchHits.getTotalHits());

		// 검색 결과 추가
		List<SportDocResponseDto> responseDtoList = new ArrayList<>();
		for(SearchHit<SportDoc> hit : searchHits) {
			responseDtoList.add(new SportDocResponseDto(hit.getContent()));
		}
		result.put("data", responseDtoList);
		return result;
	}

	public Map<String, Object> searchQuery(String queryText, int pageNumber, int pageSize) {

		// Pageable 생성
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		// JPA로 쿼리 작성
		Page<Sports> sportsList = sportsRepository
			.findBySportNameContainingOrStadium_StadiumNameContainingOrderByMatchDateDesc(queryText, queryText, pageable);

		// 리턴받을 Map 객체
		Map<String, Object> result = new HashMap<>();

		// 검색된 엔티티 갯수
		result.put("count", sportsList.getTotalElements());

		// 검색 결과 추가
		result.put("data", sportsList.stream().map(SportsResponseDto::new).toList());
		return result;
	}
}
