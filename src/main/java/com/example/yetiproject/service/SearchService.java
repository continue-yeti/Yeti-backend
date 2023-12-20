package com.example.yetiproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.yetiproject.document.SportDoc;
import com.example.yetiproject.dto.sports.SportDocResponseDto;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

	private final ElasticsearchOperations elasticsearchOperations;

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
}
