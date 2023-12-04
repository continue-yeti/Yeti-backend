package com.example.yetiproject.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.example.yetiproject.dto.user.UserSigninRequestDto;
import com.example.yetiproject.dto.user.UserSignupRequestDto;
import com.example.yetiproject.entity.User;
import com.example.yetiproject.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	// 임시
	public void signup(UserSignupRequestDto requestDto) {

		// 비밀번호 두 개가 다르면 에러 발생
		if (!StringUtils.equals(requestDto.getPassword(), requestDto.getPassword2())) {
			throw new IllegalArgumentException("두 비밀번호가 다릅니다.");
		}

		// 비밀번호 암호화
		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

		// 유저 객체를 만든 후 레포지토리에 저장
		User user = new User(requestDto);
		// user.updatePassword(encodedPassword);
		user.updatePassword(requestDto.getPassword());
		userRepository.save(user);
	}

	// 임시
	public void signin(UserSigninRequestDto requestDto) {
		User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
			() -> new EntityNotFoundException("회원이 없습니다."));

		if (!StringUtils.equals(user.getPassword(), requestDto.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
		}
	}
}
