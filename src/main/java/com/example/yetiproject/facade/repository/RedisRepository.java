package com.example.yetiproject.facade.repository;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j(topic = "RedisRepository")
public class RedisRepository {
	private final RedisTemplate<String, String> redisTemplate;

	public Boolean zAdd(String key, String value, double score){
		return redisTemplate.opsForZSet().add(key, value, score);
	}
	public Boolean zAddIfAbsent(String key, String value, double score){
		//registQueue에 사용
		return redisTemplate.opsForZSet().addIfAbsent(key, value, score);
	}

	public String get(String key){
		return redisTemplate.opsForValue().get(key);
	}

	public void set(String key, String value){
		redisTemplate.opsForValue().set(key, value);
	}

	public void delete(String key){
		redisTemplate.delete(key);
	}

	public Set<String> zRange(String key, Long start, Long end){

		return redisTemplate.opsForZSet().range(key, start, end);
	}

	public Set<ZSetOperations.TypedTuple<String>> zRangeWithScores(String key, Long start, Long end){
		return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
	}

	public Long zRank(String key, String jsonObject){
		return redisTemplate.opsForZSet().rank(key, jsonObject);
	}

	public Long zRemove(String key, String jsonObject){
		return redisTemplate.opsForZSet().remove("ticket", jsonObject);
	}

	public Long zSize(String key){
		return redisTemplate.opsForZSet().size(key);
	}

	public Long zCard(String key){
		return redisTemplate.opsForZSet().size(key);
	}

	public Double zScore(String key, String member){
		return redisTemplate.opsForZSet().score(key, member);
	}

	public Long decrease(String key){
		return redisTemplate
			.opsForValue()
			.decrement(key);
	}


}
