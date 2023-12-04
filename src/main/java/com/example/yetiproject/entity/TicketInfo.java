package com.example.yetiproject.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TicketInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ticketInfoId;

	private Date openDate;
	private Date closeDate;
	private int ticketPrice;
	private int stock;

}
