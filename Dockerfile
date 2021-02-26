FROM openjdk:8-jdk-alpine
COPY build/libs/*.jar app.jar
ENV TZ Europe/Moscow
CMD ["java", "-jar", "/app.jar"]