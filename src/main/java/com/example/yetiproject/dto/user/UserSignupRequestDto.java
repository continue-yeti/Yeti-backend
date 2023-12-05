package com.example.yetiproject.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDto {
	private String email;
	private String password;
	private String password2;
	private String username;
	private String phoneNumber;
	private String address;
}
