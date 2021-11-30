# FROM maven:3.8.3-jdk-11 as maven
# COPY ./pom.xml ./pom.xml
# COPY ./src ./src
# RUN mvn dependency:go-offline -B
# RUN mvn package

FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]