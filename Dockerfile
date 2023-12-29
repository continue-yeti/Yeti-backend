FROM openjdk:17
ARG JAR_FILE=yetiProject-0.0.1-SNAPSHOT.jar
COPY $JAR_FILE app.jar
ENTRYPOINT ["java","-jar",\
"-javaagent:/home/ubuntu/pinpoint-agent-2.5.1/pinpoint-bootstrap-2.5.1.jar",\
"-Dpinpoint.agentId=yeti",\
"-Dpinpoint.applicationName=yetiProject",\
"-Dpinpoint.config=/home/ubuntu/pinpoint-agent-2.5.1/pinpoint-root.config",\
"-Duser.timezone=Asia/Seoul", "/app.jar"]
