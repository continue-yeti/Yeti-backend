FROM openjdk:17
ARG JAR_FILE=yetiProject-0.0.1-SNAPSHOT.jar
COPY $JAR_FILE app.jar
ENTRYPOINT ["java","-jar","/app.jar", "-javaagent:./pinpoint/pinpoint-bootstrap-2.2.2.jar", "-Dpinpoint.agentId=yeti","-Dpinpoint.applicationName=yetiProject","-Dpinpoint.config=./pinpoint/pinpoint-root.config","-Dspring.profiles.active=prod","-Duser.timezone=Asia/Seoul"]
