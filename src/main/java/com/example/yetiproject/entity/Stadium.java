package com.example.yetiproject.entity;

import com.example.yetiproject.dto.stadium.StadiumCreateDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stadium")
@NoArgsConstructor
public class Stadium {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long stadiumId;

	@Column(nullable = false)
	private String stadiumName;

	public Stadium(StadiumCreateDto requestDto) {
		this.stadiumName = requestDto.getStadiumName();
	}
}
