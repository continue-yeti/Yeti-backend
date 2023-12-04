package com.example.yetiproject.entity;

import com.example.yetiproject.dto.stadium.StadiumCreateRequestDto;
import com.example.yetiproject.dto.stadium.StadiumModifyRequestDto;

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

	public Stadium(StadiumCreateRequestDto requestDto) {
		this.stadiumName = requestDto.getStadiumName();
	}

	public void update(StadiumModifyRequestDto requestDto) {
		this.stadiumName = requestDto.getStadiumName();
	}
}
