# [Yeti] 대규모 트래픽 상황에서도 원활한 티케팅이 가능한 예매 플랫폼

![image](https://github.com/Yeti-spring-project/Yeti/assets/76715487/5174be80-f960-491b-84a4-750d2ff94ea9)


## 서비스 개요  

> Yeti는 대규모 트래픽 상황에서도 원활한 티케팅이 가능한 예매 서비스입니다.

 온라인 서비스의 수요가 늘어감에 따라, 대규모 트래픽 상황을 효율적으로 관리할 수 있는 기술의 필요성이 늘어나고 있습니다. 저희 조는 이러한 흐름에 맞춰 대규모 트래픽 상황에서도 고가용성을 확보하고, 데이터의 일관성을 유지하면서도 빠른 처리속도를 보여줄 수 있는 시스템을 만들어보기로 결정했습니다. 이에, 티켓 예매 서비스가 해당 시스템을 구현하기에 적합한 주제라 생각되어 예매 서비스를 선택하게 되었습니다.

[Yeti-spring-project](https://github.com/Yeti-spring-project)
[Yeti-Notion](https://cheerful-beast-329.notion.site/Yes-Ticket-yeti-2d228454ae42461ba6023e71a3036c43)


## 아키텍쳐 구성도

![image](https://github.com/continue-yeti/Yeti-backend/assets/76715487/92ce9c79-cb5f-4738-9733-f9cd0c9b77e9)



## ERD

![image](https://github.com/continue-yeti/Yeti-backend/assets/76715487/38e218a5-581d-4216-8951-dac6067690ce)



## 주요 기능

- 대규모 트래픽 상황에서도 원활한 티케팅이 가능
- 검색 서버를 분리하여 안정적인 경기 검색 제공
- 사용자가 갑작스럽게 많아질 경우 대기열로 트래픽 조절

<br/>

## 기술 스택

| Frontend                                                                                                  | Backend                                                                                                                                                                                                                                              | Database                                                                                                                                                                                                                              | DevOps                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         | Others                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
|-----------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white"><br/> | <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/><br/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/> <br/> | <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"><br/>  <img src="https://img.shields.io/badge/postgreSQL-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white"><br/> | <img src="https://img.shields.io/badge/amazon%20ec2-FF9900?style=for-the-badge&logo=amazon%20ec2&logoColor=white"><br/> <img src="https://img.shields.io/badge/amazon%20rds-527FFF?style=for-the-badge&logo=amazon%20rds&logoColor=white"><br/> <img src="https://img.shields.io/badge/amazon%20s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white"><br/> <img src="https://img.shields.io/badge/amazon%20alb-FF9900?style=for-the-badge"/><br/> <img src="https://img.shields.io/badge/amazon%20codedeploy-569A31?style=for-the-badge"/><br/>  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"><br/> <img src="https://img.shields.io/badge/github%20actions-2088FF?style=for-the-badge&logo=github%20actions&logoColor=white"><br/> <img src="https://img.shields.io/badge/vercel-%23000000?style=for-the-badge&logo=vercel&logoColor=white"> | <img src="https://img.shields.io/badge/elasticsearch-4053D6?style=for-the-badge&logo=elasticsearch&logoColor=white"> <br/> <img src="https://img.shields.io/badge/jmeter-FF4500?style=for-the-badge"/><br/> |



## 기술적 의사 결정


| 요구사항 | 선택지 | 기술 선택 이유 |
| --- | --- | --- |
| CI/CD | Jenkins<br/> Github Action<br/> | Github Action<br/> 클라우드에서 동작하므로 다른 서버를 설치 하지 않아도 되기 때문에 가격적 측면과 자원 소모를 줄일 수 있었습니다. 또한 GitHub에 push, PR 이벤트가 발생할 때 자동 배포가 가능하여 개발에 몰두 할 수 있습니다. Blue/Green 전략을 이용해 무중단 배포로 서비스를 끊김없이 계속 이용 가능하도록 구성하였습니다. |
| 모니터링 | Elastic APM <br/>Prometheus <br/>Grafana <br/>PinPoint ✔️ | Pinpoint<br/> 전체적인 트랜잭션의 흐름을 쉽게 파악이 가능하고, 사용자가 직접 그래프를 그리지 않아도 되면서 그래프가 매우 알기 쉽게 표현됩니다. 대규모 시스템의 분산 트랜잭션 추적 및 시각화가 용이해 모니터링 APM을 Pinpoint로 선택하였습니다 |
| 데이터베이스 | MySQL <br/> PostgreSQL ✔️   <br/> MongDB | PostgreSQL <br/> 동시성 제어 성능이 MySQL에 비해 뛰어나며 다중 버전 동시성 제어를 제공합니다. 또한 쓰기 및 조회 성능이 다른 RDB에 비해 우수하다고 알려져 있기 때문에 대용량 트래픽 처리에 용이하다고 판단하였습니다. |
| 데이터 파이프 라인 | Kafka <br/> Redis ✔️ | Redis <br/> 성능이 뛰어나고 간단한 데이터 모델을 지원해 사용이 용이합니다. 그리고 메모리를 사용하면서 영속적으로 데이터를 보존 가능하며 리스트형 데이터 입력과 삭제가 데이터베이스보다 매우 빠르다는 장점이 있습니다. Kafka도 후보에 있었지만, 프로젝트의 규모나 기간을 고려할 때 추가적인 학습이 필요한 Kafka를 도입하기보다는 기존에 사용해보았던 Redis를 사용하는것이 좋다고 판단했습니다. |
| 부하테스트 | JMeter ✔️ <br/> nGrinder | JMeter <br/>간단하게 테스트하기 용이하고, GUI 기반으로 사용하기 쉽우며 플러그인 지원이 풍부합니다. 결정적으로, nGrinder가 프로젝트에서 사용하는 Java 17 버전을 지원하지 않아서 JMeter를 선택하였습니다. |



## 트러블 슈팅

<details>
<summary>트래픽이 몰리는 상황에서 TIME_WAIT socket </summary>
	
<div markdown="1">

## 문제 발생
TPS가 갑작기 줄어드는 구간에 time wait 에러가 발생하여 그라파나 모니터링 결과 time-waiting Thread가 많은 것을 확인


## 해결 과정
- 시스템에서 사용 가능한 로컬 포트의 범위를 확장하고, 동시에 더 많은 포트를 사용하도록 ip_local_port_range 범위 증가

```
echo \"10240 65535\" > /proc/sys/net/ipv4/ip_local_port_range
```

- TCP 타임스탬프 옵션을 활성화하여, TCP 연결에 타임스탬프를 포함하여 네트워크 지연을 줄이고, 연결 안정성을 향상
- TIME_WAIT 상태의 TCP 소켓을 재사용하도록하여, TCP 연결이 종료된 후에 일정시간 유지되는 상태를 재사용하여 포트 고갈 문제 해결하고 TCP 연결 수 증가
- net.ipv4.tcp_tw_reuse는 항상 net.ipv4.tcp_timestamps와 함께 사용되어야하고, net.ipv4.tcp_timestamps는 반드시 1 이어야한다.

```
sudo sysctl -w "net.ipv4.tcp_timestamps=1"
sudo sysctl -w "net.ipv4.tcp_tw_reuse=1"
```

- 시스템 전체적으로 동시에 열 수 있는 파일이나 소켓의 수가 증가시키기 위해 /etc/security/limit.conf 수정

```
*           soft    nofile          200000
*           hard    nofile          200000
```

## 결과
| m4.xlarge | 개선 전 | 개선 후 |
| --- | --- | --- | 
| request 요청 수 | 23,000 | 80,000 | 
| error 발생 % | 23%  | 0.2% | 

에러 발생율이 현저히 줄어든 것을 확인 가능

</div>
</details>


<details>
<summary>java.net.SocketException: Socket closed</summary>
<div markdown="1">

## 문제 발생
서버에 대량의 트래픽이 발생하여 서버가 초과적으로 부하되었을 때 발생


## 해결 과정

- 네트워크 디바이스에 들어오는 연결 요청(백로그)의 최대 크기 변경
백로그는 커널이 소켓에 연결을 수락할 수 있는 대기열로, 연결 요청을 처리하는 속도보다 더 빨리 발생할 수 있는 경우에 사용
```
# 조회
sysctl net.core.netdev_max_backlog

# 수정
sudo sysctl -w net.core.netdev_max_backlog=30000
```

- TCP 연결에서 SYN Backlog가 가득찼을때 SYN패킷을 SYN Backlog에 저장하지 않고 ISN(Initial Sequence Number 을 만들어서 SYN + ACK를 클라이언트로 전송하여 SYN 패킷 Drop 방지

```
# 활성화
sudo sysctl -w net.ipv4.tcp_syncookies=1
```

- TCP TIME_WAIT 소켓의 최대 개수를 제한하여 서버의 확장성과 성능 향상
```
sudo sysctl -w net.ipv4.tcp_max_tw_buckets=1800000
```

- tcp_tw_recycle 기능 on -> off 변경
네트워크 성능은 크게 향상되지만 클라이언트가 NAT/LB 환경인 경우 일부 클라이언트로 부터의 SYN 패킷이 유실 발생

```
sudo sysctl -w "net.ipv4.tcp_tw_reuse=1"
```




## 결과


</div>
</details>
 





## 성능 개선

<details>
<summary>Bulk Insert </summary>
<div markdown="1">
Hibernates에서는 IDENTITY 전략을 사용할 경우 save 할 때 데이터베이스에 일단 INSERT한 뒤 생성된 기본키를 가져온다.
그러다보니 Bulk Insert 할 때 N번 째 데이터의 기본키(식별자)를 채번 하기 위해 N-1 데이터까지 INSERT가 되어있는 상태에서 N번 째 데이터를 INSERT하고 생성된 기본키를 가져오게 되어서 실질적으로 N번의 삽입 쿼리가 발생한다.
위와 같은 채번 원리때문에 Hibernates에서는 기본적으로 IDENTITY 기본키 생성 전략을 가져갈 때 Batch Insert를 허용하지 않는다는 것을 알 수 있었다.

```
@Repository
@RequiredArgsConstructor
public class TicketJdbcBatchRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Long> batchUpdate(List<Ticket> tickets) {
        String sql = "INSERT INTO tickets (ticket_info_id, posX, posY) VALUES (?, ?, ?)";
        return Arrays.stream(
                jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Ticket ticket = tickets.get(i);
                        ps.setLong(1, ticket.getTicketInfo().getTicketInfoId());
                        ps.setLong(2, ticket.getPosX());
                        ps.setLong(3, ticket.getPosY());
                    }

                    @Override
                    public int getBatchSize() {
                        return tickets.size();
                    }
                })
        ).boxed().map(Integer::longValue).toList();
    }
}
```

문제를 해결하기 위해Hibernate의 Batch Insert 기능을 활용하였다. 
TicketJdbcBatchRepository클래스에서는 JdbcTemplate을 사용하여 JDBC로 일괄 삽입을 수행하도록 한다. 
이를 통해 Hibernate의 기본키 생성 전략으로 인한 성능 문제를 해결하였다.

</div>
</details>

<details>
<summary>Cache</summary>
<div markdown="1">
티켓을 예매하는 과정에서 항상 티켓의 날짜와 개수를 체크하여 변하지 않는 값이 계속해서 조회되는 상황
```
public Long registerQueue(Long userId, TicketRequestDto ticketRequestDto) throws JsonProcessingException {
		//오픈날짜 종료날짜를 체크한다.
		if(checkTicketInfoDate(ticketRequestDto.getTicketInfoId()) == false){
			log.info("예매가능한 날짜가 아닙니다.");
			throw ErrorCode.NOT_AVAILABLE_RESERVATION_DATES.build();
		}

		if(checkSelectedSeat(TICKETINFO_OCCUPY_SEAT.formatted(ticketRequestDto.getTicketInfoId())
			, ticketRequestDto.getSeat(), userId) == false){
			log.info("이미 선택된 좌석입니다.");
			throw ErrorCode.QUEUE_ALREADY_REGISTERED_USER.build();
		}
	
```

이를 캐싱 처리하여 성능을 개선
| request | 100 | 1000 | 5000 | 10000 | 30000 |
| --- | --- | --- | --- | --- | --- |
| 캐싱 X | 2267ms | 17076ms | 100778ms | 245767ms | 506622ms |
| 캐싱 O | 2112ms | 16716ms | 84762ms | 217015ms | 402892ms |


</div>
</details>
