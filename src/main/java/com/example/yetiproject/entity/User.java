package com.example.yetiproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long userId;

	@Column(name = "email")
	Long email;
	@Column(name = "poassword")
	Long password;
	@Column(name = "username")
	Long username;
	@Column(name = "phoneNumber")
	Long phoneNumber;
	@Column(name = "address")
	Long address;
}
