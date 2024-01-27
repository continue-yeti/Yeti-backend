# 작업 디렉토리를 /home/ubuntu으로 변경
cd /home/ubuntu


# 환경변수 DOCKER_APP_NAME 설정
DOCKER_APP_NAME=ec2-application

# 실행 중인 app이 있는지 확인
# 프로젝트의 실행 중인 컨테이너를 확인하고, 해당 컨테이너가 실행 중인지를 EXIST_APP 변수에 저장
EXIST_APP=$(sudo docker-compose -p ${DOCKER_APP_NAME} -f docker-compose.yml ps | grep Up)

# 배포 시작한 날짜와 시간을 기록
echo "배포 시작 일자 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

# EXIST_APP 변수가 비어있는지 확인
if [ -z "$EXIST_APP" ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종류하지 않겠습니다."
  # 로그 파일(/home/ubuntu/deploy.log)에 "app 배포 : 8080:8080"이라는 내용을 추가
  echo "app 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

	# docker-compose.yml 파일을 사용하여 seniors-blue 프로젝트의 컨테이너를 빌드하고 실행
	sudo docker-compose -p ${DOCKER_APP_NAME} -f docker-compose.yml up -d --build
  # 30초 동안 대기
  sleep 30
  # app 문제없이 배포되었는지 현재 실행 여부를 확인
  APP_HEALTH=$(sudo docker-compose -p ${DOCKER_APP_NAME} -f docker-compose.yml ps | grep Up)

  # app 현재 실행 중이지 않다면 -> 런타임 에러 또는 다른 이유로 배포가 되지 못한 상태
  if [ -z "$APP_HEALTH" ]; then
    # slack으로 알람을 보낼 수 있는 스크립트를 실행
    sudo /home/ubuntu/slack_app.sh
  fi
else
  echo "> docker old-ec2-application 종료하겠습니다." >> /home/ubuntu/deploy.log
  echo "이전버전 중단 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

  sudo docker-compose -p ${DOCKER_APP_NAME} -f docker-compose.yml down
  sudo docker image prune -af
  echo "이전버전 삭제 완료 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log
fi
