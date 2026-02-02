FROM eclipse-temurin:17-jre

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 jar 파일을 docker 이미지 내부로 복사
COPY build/libs/*.jar app.jar

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
