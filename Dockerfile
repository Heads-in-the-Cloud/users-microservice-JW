# FROM openjdk:11-jre-slim
FROM maven:3.6.3-jdk-11
RUN mvn package
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

