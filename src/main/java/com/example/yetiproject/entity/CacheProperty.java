package com.example.yetiproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CacheProperty {
	private String name;
	private Integer ttl;
}
