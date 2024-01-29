# [Yeti] 대규모 트래픽 상황에서도 원활한 티케팅이 가능한 예매 플랫폼

---

![image](https://github.com/Yeti-spring-project/Yeti/assets/76715487/5174be80-f960-491b-84a4-750d2ff94ea9)


# 🎫 01. 프로젝트 소개

## 1️⃣ 서비스 개요  

> Yeti는 대규모 트래픽 상황에서도 원활한 티케팅이 가능한 예매 서비스입니다.
> 

 온라인 서비스의 수요가 늘어감에 따라, 대규모 트래픽 상황을 효율적으로 관리할 수 있는 기술의 필요성이 늘어나고 있습니다. 저희 조는 이러한 흐름에 맞춰 대규모 트래픽 상황에서도 고가용성을 확보하고, 데이터의 일관성을 유지하면서도 빠른 처리속도를 보여줄 수 있는 시스템을 만들어보기로 결정했습니다. 이에, 티켓 예매 서비스가 해당 시스템을 구현하기에 적합한 주제라 생각되어 예매 서비스를 선택하게 되었습니다.

[Yeti-spring-project](https://github.com/Yeti-spring-project)
[Yeti-Notion](https://cheerful-beast-329.notion.site/Yes-Ticket-yeti-2d228454ae42461ba6023e71a3036c43)

## 2️⃣ 프로젝트 목표

![image](https://github.com/Yeti-spring-project/Yeti/assets/76715487/14374ebe-6e62-4104-80b9-75752650daf7)


- 대규모 트래픽에 대한 고가용성 확보
- 동시성 제어 및 최적화
- 확장성 강화 및 모듈화

## 3️⃣ 주요 기능

- 대규모 트래픽 상황에서도 원활한 티케팅이 가능
- 검색 서버를 분리하여 안정적인 경기 검색 제공
- 사용자가 갑작스럽게 많아질 경우 대기열로 트래픽 조절

---

# 🎫 02. 기술 스택

| Frontend                                                                                                  | Backend                                                                                                                                                                                                                                              | Database                                                                                                                                                                                                                              | DevOps                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         | Others                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
|-----------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white"><br/> | <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/><br/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/> <br/> | <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"><br/>  <img src="https://img.shields.io/badge/postgreSQL-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white"><br/> | <img src="https://img.shields.io/badge/amazon%20ec2-FF9900?style=for-the-badge&logo=amazon%20ec2&logoColor=white"><br/> <img src="https://img.shields.io/badge/amazon%20rds-527FFF?style=for-the-badge&logo=amazon%20rds&logoColor=white"><br/> <img src="https://img.shields.io/badge/amazon%20s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white"><br/> <img src="https://img.shields.io/badge/amazon%20alb-FF9900?style=for-the-badge"/><br/> <img src="https://img.shields.io/badge/amazon%20codedeploy-569A31?style=for-the-badge"/><br/>  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"><br/> <img src="https://img.shields.io/badge/github%20actions-2088FF?style=for-the-badge&logo=github%20actions&logoColor=white"><br/> <img src="https://img.shields.io/badge/vercel-%23000000?style=for-the-badge&logo=vercel&logoColor=white"> | <img src="https://img.shields.io/badge/elasticsearch-4053D6?style=for-the-badge&logo=elasticsearch&logoColor=white"><br/> <img src="https://img.shields.io/badge/logstash-FCC624?style=for-the-badge&logo=logstash&logoColor=white"><br/> <img src="https://img.shields.io/badge/kibana-00bfb3?style=for-the-badge&logo=kibana"/><br/> <img src="https://img.shields.io/badge/pinpoint-007ACC?style=for-the-badge"/><br/> <img src="https://img.shields.io/badge/jmeter-FF4500?style=for-the-badge"/><br/> |


---

# 🎫 03. 기술적 의사 결정


| 요구사항 | 선택지 | 기술 선택 이유 |
| --- | --- | --- |
| CI/CD | Jenkins<br/> Github Action<br/> | Github Action<br/> 클라우드에서 동작하므로 다른 서버를 설치 하지 않아도 되기 때문에 가격적 측면과 자원 소모를 줄일 수 있었습니다. 또한 GitHub에 push, PR 이벤트가 발생할 때 자동 배포가 가능하여 개발에 몰두 할 수 있습니다. Blue/Green 전략을 이용해 무중단 배포로 서비스를 끊김없이 계속 이용 가능하도록 구성하였습니다. |
| 모니터링 | Elastic APM <br/>Prometheus <br/>Grafana <br/>PinPoint ✔️ | Pinpoint<br/> 전체적인 트랜잭션의 흐름을 쉽게 파악이 가능하고, 사용자가 직접 그래프를 그리지 않아도 되면서 그래프가 매우 알기 쉽게 표현됩니다. 대규모 시스템의 분산 트랜잭션 추적 및 시각화가 용이해 모니터링 APM을 Pinpoint로 선택하였습니다 |
| 검색 성능 개선 | MongoDB<br/>Elasticsearch✔️ | Elasticsearch <br/> RDBMS에서도 텍스트 검색을 위한 기능을 제공하지만, 와일드카드를 사용하거나 복잡한 패턴을 사용한 검색에 있어서 성능저하가 우려되었습니다. 따라서 검색 성능 개선을 위해 NoSQL DB인 MongoDB와 Elasticsearch를 고려했고, 최종적으로 Elasticsearch를 선택하게 되었습니다.  Elasticsearch를 선택한 이유로는 Elasticsearch가 제공하는 강력한 풀 텍스트 검색 기능과 QueryDSL이 프로젝트에 좀 더 적합하다고 생각했기 때문입니다. 또한 이후 확장성을 고려해보았을 때에도 분산형 시스템으로 설계된 Elasticsearch를 사용하는 것이 유리하다고 생각되었습니다. |
| 데이터베이스 | MySQL <br/> PostgreSQL ✔️ <br/> MongDB | PostgreSQL <br/> 동시성 제어 성능이 MySQL에 비해 뛰어나며 다중 버전 동시성 제어를 제공합니다. 또한 쓰기 및 조회 성능이 다른 RDB에 비해 우수하다고 알려져 있기 때문에 대용량 트래픽 처리에 용이하다고 판단하였습니다. |
| 데이터 파이프 라인 | Kafka <br/> Redis ✔️ | Redis <br/> 성능이 뛰어나고 간단한 데이터 모델을 지원해 사용이 용이합니다. 그리고 메모리를 사용하면서 영속적으로 데이터를 보존 가능하며 리스트형 데이터 입력과 삭제가 데이터베이스보다 매우 빠르다는 장점이 있습니다. Kafka도 후보에 있었지만, 프로젝트의 규모나 기간을 고려할 때 추가적인 학습이 필요한 Kafka를 도입하기보다는 기존에 사용해보았던 Redis를 사용하는것이 좋다고 판단했습니다. |
| 부하테스트 | JMeter ✔️ <br/> nGrinder | JMeter <br/>간단하게 테스트하기 용이하고, GUI 기반으로 사용하기 쉽우며 플러그인 지원이 풍부합니다. 결정적으로, nGrinder가 프로젝트에서 사용하는 Java 17 버전을 지원하지 않아서 JMeter를 선택하였습니다. |

# 🎫 04. 아키텍쳐

## 아키텍쳐 구성도

![image](https://github.com/Yeti-spring-project/Yeti/assets/76715487/d4a8c4e1-86c1-4557-a992-93775b193b6b)


## ERD

![image](https://github.com/Yeti-spring-project/Yeti/assets/76715487/b10c122b-d15c-475b-9625-4b7035b4b305)


---

# 🎫 05. 트러블 슈팅

### 동시성 제어 미적용시에 DeadLock이 발생하는 문제 

- 티켓 22개가 예매 되다가 이후에 데드락이 걸렸습니다. ( 89 → 67 )

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/76069a67-bbe8-437e-b3bc-6afc7dcff7d5/8e1bb664-2a91-4896-928f-18833bc0a985/Untitled.png)

- Spring 에러 로그
    
    ```java
    2023-12-10T18:11:44.182+09:00 ERROR 36492 --- [nio-8080-exec-4] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.dao.CannotAcquireLockException: could not execute statement [Deadlock found when trying to get lock; try restarting transaction] [/* update for com.example.yetiproject.entity.TicketInfo */update ticket_info set close_date=?,open_date=?,sports_id=?,stock=?,ticket_price=? where ticket_info_id=?]; SQL [/* update for com.example.yetiproject.entity.TicketInfo */update ticket_info set close_date=?,open_date=?,sports_id=?,stock=?,ticket_price=? where ticket_info_id=?]] with root cause
    
    com.mysql.cj.jdbc.exceptions.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction
    	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:124) ~[mysql-connector-j-8.1.0.jar:8.1.0]
    	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122) ~[mysql-connector-j-8.1.0.jar:8.1.0]
    	at com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:916) ~[mysql-connector-j-8.1.0.jar:8.1.0]
    	at com.mysql.cj.jdbc.ClientPreparedStatement.executeUpdateInternal(ClientPreparedStatement.java:1061) ~[mysql-connector-j-8.1.0.jar:8.1.0]
    	at com.mysql.cj.jdbc.ClientPreparedStatement.executeUpdateInternal(ClientPreparedStatement.java:1009) ~[mysql-connector-j-8.1.0.jar:8.1.0]
    	at com.mysql.cj.jdbc.ClientPreparedStatement.executeLargeUpdate(ClientPreparedStatement.java:1320) ~[mysql-connector-j-8.1.0.jar:8.1.0]
    	at com.mysql.cj.jdbc.ClientPreparedStatement.executeUpdate(ClientPreparedStatement.java:994) ~[mysql-connector-j-8.1.0.jar:8.1.0]
    	at com.zaxxer.hikari.pool.ProxyPreparedStatement.executeUpdate(ProxyPreparedStatement.java:61) ~[HikariCP-5.0.1.jar:na]
    	at com.zaxxer.hikari.pool.HikariProxyPreparedStatement.executeUpdate(HikariProxyPreparedStatement.java) ~[HikariCP-5.0.1.jar:na]
    	at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.executeUpdate(ResultSetReturnImpl.java:280) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.performNonBatchedMutation(AbstractMutationExecutor.java:107) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.engine.jdbc.mutation.internal.MutationExecutorSingleNonBatched.performNonBatchedOperations(MutationExecutorSingleNonBatched.java:40) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.execute(AbstractMutationExecutor.java:52) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.persister.entity.mutation.UpdateCoordinatorStandard.doStaticUpdate(UpdateCoordinatorStandard.java:769) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.persister.entity.mutation.UpdateCoordinatorStandard.performUpdate(UpdateCoordinatorStandard.java:325) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.persister.entity.mutation.UpdateCoordinatorStandard.coordinateUpdate(UpdateCoordinatorStandard.java:242) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.persister.entity.AbstractEntityPersister.update(AbstractEntityPersister.java:2802) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.action.internal.EntityUpdateAction.execute(EntityUpdateAction.java:166) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:631) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:498) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.event.internal.AbstractFlushingEventListener.performExecutions(AbstractFlushingEventListener.java:363) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.event.internal.DefaultFlushEventListener.onFlush(DefaultFlushEventListener.java:39) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.internal.SessionImpl.doFlush(SessionImpl.java:1415) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.internal.SessionImpl.managedFlush(SessionImpl.java:496) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.internal.SessionImpl.flushBeforeTransactionCompletion(SessionImpl.java:2325) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.internal.SessionImpl.beforeTransactionCompletion(SessionImpl.java:1988) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.engine.jdbc.internal.JdbcCoordinatorImpl.beforeTransactionCompletion(JdbcCoordinatorImpl.java:439) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl.beforeCompletionCallback(JdbcResourceLocalTransactionCoordinatorImpl.java:169) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl$TransactionDriverControlImpl.commit(JdbcResourceLocalTransactionCoordinatorImpl.java:267) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.hibernate.engine.transaction.internal.TransactionImpl.commit(TransactionImpl.java:101) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
    	at org.springframework.orm.jpa.JpaTransactionManager.doCommit(JpaTransactionManager.java:561) ~[spring-orm-6.1.1.jar:6.1.1]
    	at org.springframework.transaction.support.AbstractPlatformTransactionManager.processCommit(AbstractPlatformTransactionManager.java:794) ~[spring-tx-6.1.1.jar:6.1.1]
    	at org.springframework.transaction.support.AbstractPlatformTransactionManager.commit(AbstractPlatformTransactionManager.java:757) ~[spring-tx-6.1.1.jar:6.1.1]
    	at org.springframework.transaction.interceptor.TransactionAspectSupport.commitTransactionAfterReturning(TransactionAspectSupport.java:669) ~[spring-tx-6.1.1.jar:6.1.1]
    	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:419) ~[spring-tx-6.1.1.jar:6.1.1]
    	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119) ~[spring-tx-6.1.1.jar:6.1.1]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.1.1.jar:6.1.1]
    	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:765) ~[spring-aop-6.1.1.jar:6.1.1]
    	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:717) ~[spring-aop-6.1.1.jar:6.1.1]
    	at com.example.yetiproject.service.TicketService$$SpringCGLIB$$0.reserveTicket(<generated>) ~[main/:na]
    	at com.example.yetiproject.controller.TicketController.reserveTicket(TicketController.java:50) ~[main/:na]
    	at jdk.internal.reflect.GeneratedMethodAccessor64.invoke(Unknown Source) ~[na:na]
    	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
    	at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
    	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:254) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:182) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118) ~[spring-webmvc-6.1.1.jar:6.1.1]
    	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:917) ~[spring-webmvc-6.1.1.jar:6.1.1]
    	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:829) ~[spring-webmvc-6.1.1.jar:6.1.1]
    	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-6.1.1.jar:6.1.1]
    	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1089) ~[spring-webmvc-6.1.1.jar:6.1.1]
    	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:979) ~[spring-webmvc-6.1.1.jar:6.1.1]
    	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014) ~[spring-webmvc-6.1.1.jar:6.1.1]
    	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:914) ~[spring-webmvc-6.1.1.jar:6.1.1]
    	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590) ~[tomcat-embed-core-10.1.16.jar:6.0]
    	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885) ~[spring-webmvc-6.1.1.jar:6.1.1]
    	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658) ~[tomcat-embed-core-10.1.16.jar:6.0]
    	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:205) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51) ~[tomcat-embed-websocket-10.1.16.jar:10.1.16]
    	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:110) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:227) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:221) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.springframework.security.web.FilterChainProxy.lambda$doFilterInternal$3(FilterChainProxy.java:231) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:365) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.access.intercept.AuthorizationFilter.doFilter(AuthorizationFilter.java:100) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:126) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:120) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:131) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:85) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:100) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:179) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter.doFilterInternal(DefaultLogoutPageGeneratingFilter.java:58) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter.doFilter(DefaultLoginPageGeneratingFilter.java:189) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter.doFilter(DefaultLoginPageGeneratingFilter.java:175) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:227) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:221) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:227) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:221) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at com.example.yetiproject.auth.jwt.JwtAuthorizationFilter.doFilterInternal(JwtAuthorizationFilter.java:55) ~[main/:na]
    	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:107) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:93) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:91) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:90) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:75) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.context.SecurityContextHolderFilter.doFilter(SecurityContextHolderFilter.java:82) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.context.SecurityContextHolderFilter.doFilter(SecurityContextHolderFilter.java:69) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:62) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.session.DisableEncodeUrlFilter.doFilterInternal(DisableEncodeUrlFilter.java:42) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:233) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:191) ~[spring-security-web-6.2.0.jar:6.2.0]
    	at org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:352) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:268) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.1.jar:6.1.1]
    	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:482) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:340) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:391) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:896) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1744) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-10.1.16.jar:10.1.16]
    	at java.base/java.lang.Thread.run(Thread.java:842) ~[na:na]
    ```
    

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/76069a67-bbe8-437e-b3bc-6afc7dcff7d5/5e85a925-1b82-4e59-8f47-55b4480fa958/Untitled.png)

<aside>
💡 P1과 P2가 리소스 A, B 둘 다를 얻어야 한다고 가정할 때,t1에 P1이 리소를 A를 얻고 P2가 리소스 B를 얻었다면 t2때 P1은 리소스 B를, P2는 리소스 A를 기다리게 됩니다.하지만 서로 원하는 리소스가 상대방에게 할당되어 있기 때문에 이 두 프로세스는 무한정 기다리게 되는데 이러한 상태을 DeadLock상태라고 합니다.

</aside>

- 해결
    - Redisson으로 분산락을 적용시켜 동시성 제어를 적용시키니 DeadLock 현상이 사라졌다.

### 동시성 제어 적용 후 Request가 일부 실패하는 문제

> 처음 대용량 트래픽을 처리하기 위해 고민했던 문제는 ‘동시성 제어’ 이슈였다.
프론트에 익숙하지 않았던 우리는 회원가입-로그인-티켓목록페이지에서 
바로 예매를 하도록 페이지를 구성했다. 그 이후 배포 후 10만건의 데이터를 Jmeter를 전송하여 테스트해보니 2만건만 request에 성공했다.
이후 코드를 살펴보니 락을 경기관람(ticketInfoId)에 걸고 있었다.
> 

```java
public TicketResponseDto reserveTicket(UserDetailsImpl userDetails, TicketRequestDto requestDto) {
        Long ticketInfoId = requestDto.getTicketInfoId();
        RLock lock = redissonClient.getLock(ticketInfoId.toString());
        TicketResponseDto responseDto;

... (생략)
}
```

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/76069a67-bbe8-437e-b3bc-6afc7dcff7d5/53c9aa3e-0271-4bfb-9f41-881b5a36bbe6/Untitled.png)

- 현재 코드의 문제점
    - 좌석을 예매하는 것이기 때문에 좌석이 같은 사람들에 대해 락이 걸려야 한다.
    - 필요없는 한 사람까지도 락이 걸리는 것이다. 만약 100명 한 경기관람을 예매하기 위해 ‘예매하기’를 누르자마자 락을 획득하기 위해 시도한다. 코드의 개선이 필요했다.
    
    ## Seat Entity 추가
    
    ```java
    public class Seat {
    	private Long posX;
    	private Long posY;
    
    	public Seat(Long posX, Long posY) {
    		this.posX = posX;
    		this.posY = posY;
    	}
    }
    ```
    

```java
public void reserveTicket(User user, TicketRequestDto ticketRequestDto){
		Seat seat = new Seat(ticketRequestDto.getPosX(), ticketRequestDto.getPosY());
		RLock lock = redissonClient.getLock("ticket seat- " + seat);

...(생략)

}
```

## Redis의 Redisson을 선택한 이유

- 자바 스프링 기반의 웹 애플리케이션은 멀티스레드 환경에서 구동이 된다.
- 그렇기 때문에 공유 자원에 대한 Race condition이 발생하지 않도록 별도 처리가 필요하다.
- synchronized라는 상호배제 기능을 제공하지만 같은 프로세스에서만 상호 배제를 보장한다.
- 단 하나만 사용하는 서비스라면 상관없지만 나중에 서버를 다중화한다면 다시 문제가 생길 수 있다.
- Lettue Lock는 setnx 메서드를 통해 사용자가 직접 스핀락 형태로 구성한다. 락 점유에 실패하면 계속 점유 시도를 하기때문에 레디스의 부하를 일으키게 된다.
- Lettuce의 경우 만료시간을 제공하고 있지 않기 때문에, Redission과는 다르게 만약 Lock을 잡고 있는 상태에서 장애가 발생한다면 다른 서버들에서 락을 점유 할 수 없다.

---

> 동시성 문제는 해결되었지만 여전히 트래픽이 갑자기 70만건 50만건 몰리게 되는 문제에 대해서 고려를 해야 했다. 그래서 선택하게 된 것은 Redis로 대기열을 구성하는 방법이다.
> 

### Lock 획득 시도 실패 및 Lock 반환 문제

- Lock 획득이 실패하면서 아래와 같은 에러가 발생하였다.
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/76069a67-bbe8-437e-b3bc-6afc7dcff7d5/8ed43459-9168-445d-99ce-83a7a3449bcb/Untitled.png)
    
- 시도
    1. 위 에러는 Lock을 획득하지 못한 스레드가 Lock을 해제하려고 시도할 때 발생하는 에러로, 아래 코드의 try~catch~finally 구문 중 finally 부분에서 현재 스레드가 Lock을 획득했는지를 검증하는 `isHeldByCurrentThread()` 메서드를 조건문에 추가해 해결하였다.
        
        ```java
        @Component
        @Slf4j(topic = "예매하기")
        @RequiredArgsConstructor
        public class RedissonLockTicketFacade {
        
            private final RedissonClient redissonClient;
            private final TicketService ticketService;
        
            // 예매하기 부분에 Redisson으로 락을 걸어줌
            public TicketResponseDto reserveTicket(UserDetailsImpl userDetails, TicketRequestDto requestDto) {
                Long ticketInfoId = requestDto.getTicketInfoId();
                RLock lock = redissonClient.getLock(ticketInfoId.toString());
                TicketResponseDto responseDto;
        
                try {
                    // lock 획득 시도 시간, lock 점유 시간
                    boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
        
                    if (!available) {
                        log.info("lock 획득 실패");
                        return new TicketResponseDto();
                    }
                    responseDto = ticketService.reserveTicket(userDetails.getUser(), requestDto);
        
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (lock.isHeldByCurrentThread()) { // 추가
                        lock.unlock();
                    }
                }
        
                return responseDto;
            }
        }
        ```
        
    2. 해당 메서드를 적용시키니 IllegalMonitorStateException은 더 이상 발생하지 않았지만, 여전히 Lock 획득에 실패하는 스레드가 발생하였다. 대량의 요청이 동시에 들어오는 과정에서 Lock을 획득하기 위해 대기하는 시간이 기존에 설정값이었던 10초 이상으로 길어져서 발생하는 에러라고 판단하여 Lock 획득을 위해 대기하는 시간을 30초로 연장시켰다. 이 후 Lock 획득과 관련된 에러는 발생하지 않았지만, 서버에서 모든 요청을 처리하지 못하는 상황은 계속었다.
    3. Redisson으로 분산락을 적용시키는 프로세스가 리소스를 많이 사용해 서버에 장애를 일으키는 것이라 판단하여 분산락을 적용시키는 로직을 제거하고, 동시성 제어를 위해 Redis의 List나 Sorted set을 이용한 Queue 시스템을 도입하기로 하였다.

### HBASE & JDK17 버전 이슈

- 본 프로젝트에서 JDK17 버전을 사용하고 있어 모니터링 서버에도 자연스럽게 JDK17 버전을 설치했다. HBASE도 설치한 뒤 실행시켰는데 아래와 같은 오류가 노출됐다.

```docker
Unrecognized VM option 'UseConcMarkSweepGC'
Error: Could not create the Java Virtual Machine.
Error: A fatal exception has occurred. Program will exit.
Unrecognized VM option 'UseConcMarkSweepGC'
Error: Could not create the Java Virtual Machine.
Error: A fatal exception has occurred. Program will exit.
running master, logging to /hbase-2.2.0/bin/../logs/hbase-root-master-localhost.out
Unrecognized VM option 'UseConcMarkSweepGC'
Error: Could not create the Java Virtual Machine.
Error: A fatal exception has occurred. Program will exit.
: running regionserver, logging to /hbase-2.2.0/bin/../logs/hbase-root-regionserver-localhost.out
: Unrecognized VM option 'UseConcMarkSweepGC'
: Error: Could not create the Java Virtual Machine.
: Error: A fatal exception has occurred. Program will exit.
```

- 해결
    - CMS garbage collector가 JDK 15에서 제거되었으므로 UseConcMarkSweepGC도 제거되어서 발생한 에러라고 한다. JDK11로 버전을 내려서 오류를 해결하였다.

### HBASE & JDK11 관련 테이블 생성 이슈

- HBASE 활성화 이후 데이터를 담을 테이블이 필요해 pinpoint에서 공식적으로 지원하는 스크립트를 받아 적용하려 시도했더니, hbase shell 실행 단계에서 ArgumentError: wrong number of arguments (0 for 1) 라는 오류가 발생했다.

```docker
$ bin/hbase  shell
Java HotSpot(TM) 64-Bit Server VM warning: Option UseConcMarkSweepGC was deprecated in version 9.0 and will likely be removed in a future release.
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.jruby.java.invokers.RubyToJavaInvoker (file:/Users/user/Hbase/Hb126/lib/jruby-complete-1.6.8.jar) to method java.lang.Object.registerNatives()
WARNING: Please consider reporting this to the maintainers of org.jruby.java.invokers.RubyToJavaInvoker
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
ArgumentError: wrong number of arguments (0 for 1)
  method_added at file:/Users/user/Hbase/Hb126/lib/jruby-complete-1.6.8.jar!/builtin/javasupport/core_ext/object.rb:10
  method_added at file:/Users/user/Hbase/Hb126/lib/jruby-complete-1.6.8.jar!/builtin/javasupport/core_ext/object.rb:129
       Pattern at file:/Users/user/Hbase/Hb126/lib/jruby-complete-1.6.8.jar!/builtin/java/java.util.regex.rb:2
        (root) at file:/Users/user/Hbase/Hb126/lib/jruby-complete-1.6.8.jar!/builtin/java/java.util.regex.rb:1
       require at org/jruby/RubyKernel.java:1062
        (root) at file:/Users/user/Hbase/Hb126/lib/jruby-complete-1.6.8.jar!/builtin/java/java.util.regex.rb:42
        (root) at /Users/user/Hbase/Hb126/bin/../bin/hirb.rb:38
```

- 해결
    - JDK 버전을 8로 다운 후 재시도 했더니 성공하였다.
        
        [Can't connect Hbase shell](https://stackoverflow.com/questions/48471168/cant-connect-hbase-shell)
        

### 도커 용량 문제

- 프로젝트 진행 도중 도커가 실행이 안되는 문제가 발생했다.

```docker
Error response from daemon: Cannot restart container 876cbda761eb: mkdir /var/lib/docker/overlay2/fcdb67d11c4602ef250e147e9c9cba056919580f66f553ff94488084f8ce5b59/merged: no space left on device
```

- 시도
    1. Docker를 삭제 후 재설치하니 제대로 동작하였다. 
    하지만 매 번 재설치를 할 수 없으므로 다른 해결책을 찾아보기로하였다.
    2. EC2 인스턴스의 용량을 늘려 처리하였다. (Scale Up)
- 해결
    - Docker에 사용하지 않는 리소스들이 계속 누적되어서 발생하는 현상인 것으로 확인했고, 사용하지 않는 리소스들을 주기적으로 삭제하여 해결하였다.
