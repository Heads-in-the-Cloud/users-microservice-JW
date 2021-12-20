# FROM openjdk:11-jre-slim
FROM maven:3.6.3-jdk-11
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn package
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

