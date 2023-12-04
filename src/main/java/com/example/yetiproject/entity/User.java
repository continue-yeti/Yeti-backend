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
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long userId;

	@Column(name = "email")
	String email;
	@Column(name = "poassword")
	String password;
	@Column(name = "username")
	String username;
	@Column(name = "phoneNumber")
	String phoneNumber;
	@Column(name = "address")
	String address;
}
