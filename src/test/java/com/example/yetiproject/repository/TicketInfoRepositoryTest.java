package com.example.yetiproject.repository;

import java.sql.Timestamp;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class TicketInfoRepositoryTest {
	@MockBean
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	TicketInfoRepository ticketInfoRepository;

	@Test
	@DisplayName("날짜 계산을 위한 확인")
	void test1(){
		// given
		Timestamp today = new Timestamp(System.currentTimeMillis());
		List<Timestamp[]> dates = ticketInfoRepository.getOpenDateCloseDateforTicketInfo(1L);
		Timestamp openDate = dates.get(0)[0];
		Timestamp closeDate = dates.get(0)[1];

		System.out.println(today.getTime() - openDate.getTime());
		System.out.println(closeDate.getTime() - today.getTime());

		// when
		String result = "";
		if((today.getTime() - openDate.getTime() > 0) && (closeDate.getTime() - today.getTime() > 0)){
			result = "true";
		}
		else{
			result = "false";
		}

		//then
		Assertions.assertThat(result).isEqualTo("true");
	}

	@Test
	@DisplayName("오픈날짜 예매 가능하지 않을때 false을 제대로 반환하는가")
	void test2(){
		// given
		Timestamp today = new Timestamp(System.currentTimeMillis());
		List<Timestamp[]> dates = ticketInfoRepository.getOpenDateCloseDateforTicketInfo(4L);
		Timestamp openDate = dates.get(0)[0];
		Timestamp closeDate = dates.get(0)[1];

		System.out.println(today.getTime() - openDate.getTime());
		System.out.println(closeDate.getTime() - today.getTime());

		// when
		String result = "";
		if((today.getTime() - openDate.getTime() > 0) && (closeDate.getTime() - today.getTime() > 0)){
			result = "true";
		}
		else{
			result = "false";
		}

		//then
		Assertions.assertThat(result).isEqualTo("false");
	}

	@Test
	@DisplayName("예매종료날짜가 예매 가능하지 않을때 false을 제대로 반환하는가")
	void test3(){
		// given
		Timestamp today = new Timestamp(System.currentTimeMillis());
		List<Timestamp[]> dates = ticketInfoRepository.getOpenDateCloseDateforTicketInfo(5L);
		Timestamp openDate = dates.get(0)[0];
		Timestamp closeDate = dates.get(0)[1];

		System.out.println(today.getTime() - openDate.getTime());
		System.out.println(closeDate.getTime() - today.getTime());

		// when
		String result = "";
		if((today.getTime() - openDate.getTime() > 0) && (closeDate.getTime() - today.getTime() > 0)){
			result = "true";
		}
		else{
			result = "false";
		}

		//then
		Assertions.assertThat(result).isEqualTo("false");
	}
}
