package com.example.yetiproject.service;

import com.example.yetiproject.dto.sports.SportsRequestDto;
import com.example.yetiproject.dto.sports.SportsResponseDto;
import com.example.yetiproject.entity.Sports;
import com.example.yetiproject.entity.Stadium;
import com.example.yetiproject.exception.entity.sports.SportsNotFoundException;
import com.example.yetiproject.repository.SportsRepository;
import com.example.yetiproject.repository.StadiumRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class) // 1. Mockito 관련 초기화, MockBean의 초기화 등등을 자동으로 초기화
public class SportServiceTest {
    /**
     * 2. 테스트하고자하는 객체. 현재 sportsService 객체가 의존하는 객체는
     * SportsRepository, StadiumRepository 2개
     * @InjectMocks 어노테이션이 붙은 이유는 의존하는 실제 객체 대신에
     * @Mock 어노테이션이 붙은 객체들을 Mock 객체로 주입받기 위함이다.
     **/
    @InjectMocks
    private SportsService sportsService;

    /**
     * 3. @Mock 어노테이션이 붙은 객체들은 테스트하고자 하는 Service 객체가 사용하는 객체들이며,
     * 이들의 정상 동작 여부는 테스트 결과에 영향을 끼치지 않도록 테스트 코드를 작성한다.
     */
    @Mock
    private SportsRepository sportsRepository;
    @Mock
    private StadiumRepository stadiumRepository;

    static SportsRequestDto requestDto;

    /**
     * 4. @Before 어노테이션은 테스트 클래스 내에서 한번만 실행되어야 함을 나태나며,
     * 이 메서드는 테스트 클래스 내의 모든 테스트 메서드가 실행되기 전에 실행된다.
     * static 메서드로 정의되어야 한다.
     */
    @BeforeAll
    static void beforeAll() {
        requestDto = SportsRequestDto.builder()
                .stadiumId(1L)
                .sportName("2023 K리그 축구 결승")
                .matchDate("2023-12-25")
                .build();
    }

    @Test
    @DisplayName("경기 생성")
    void test1() {
        // given
        /**
         * 5. 경기 생성을 하기 위해서는 SportsService 클래스를 참고하여 수행되는 로직이 어떤 흐름인지 파악
         * 5-1. findById 함수로  stadiumRepository.findById() 실행
         * 5-2. 5-1에서 찾은 id와 requestDto로 Sprots 객체 생성
         * 5-3. sportsRepository.save()를 통해 저장
         */

        Stadium stadium = new Stadium(); // Sports 생성에 필요한 경기장
        /**
         * given() or when() : 메서드 호출에 대한 기대 동작을 설정
         */
        given(stadiumRepository.findById(any())).willReturn(Optional.of(stadium));

        // when
        SportsService sportsService = new SportsService(sportsRepository, stadiumRepository); // 5-2. 객체 생성
        String result = sportsService.createSports(requestDto);

        /**
         * 실행이 완료되었을 때 실제 결과와 예상한 결과가 같은지 확인
         */
        // then
        System.out.println(result);
        assertEquals("경기가 등록되었습니다", result);
    }

//    @Test
//    @DisplayName("경기 리스트 조회")
//    void test2() {
//        //when
//        SportsService sportsService = new SportsService(sportsRepository, stadiumRepository);
//        List<SportsResponseDto> sportsList = sportsService.getSports();
//
//        // then
//        System.out.println("sports size = " + sportsList.size());
//        assertEquals(sportsList.size(), 0);
//    }

    @Test
    @DisplayName("경기 상세 조회 예외 확인")
    void test3() {
        //given
        Long sportId = 1L;

        //when-then
        SportsService sportsService = new SportsService(sportsRepository, stadiumRepository);
        Exception exception = assertThrows(SportsNotFoundException.class, () ->
                sportsService.getSportsInfo(sportId));
        assertEquals("존재하지 않는 경기입니다", exception.getMessage());

    }

    @Test
    @DisplayName("경기 수정")
    void test4() {
        //given
        Long sportId = 1L;
        Stadium stadium = Stadium.builder().stadiumId(1L).stadiumName("고척돔").build();
        Sports existiingSports = Sports.builder().sportId(1L).sportName("K 스포츠").build();

        given(sportsRepository.findById(sportId)).willReturn(Optional.of(existiingSports));
        given(stadiumRepository.findById(stadium.getStadiumId())).willReturn(Optional.of(stadium));

        //when
        SportsResponseDto responseDto = sportsService.updateSport(sportId, requestDto);

        //then
        verify(sportsRepository, times(1)).findById(sportId);
        verify(stadiumRepository, times(1)).findById(stadium.getStadiumId());
        verify(sportsRepository, times(1)).save(existiingSports);
        assertEquals(responseDto.getId(), sportId);
    }


    @Test
    @DisplayName("경기 삭제")
    void test5() {
        //given
        Long sportId = 1L;
        Sports sport = new Sports();

        given(sportsRepository.findById(sportId)).willReturn(Optional.of(sport));

        //when
        String result = sportsService.deleteSport(sportId);

        //then
        assertEquals(result, "경기가 삭제되었습니다");

    }
}
