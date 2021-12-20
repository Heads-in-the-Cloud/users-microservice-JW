FROM maven:3.6.3-jdk-11
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn package
RUN pwd
RUN ls -l

FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar
RUN pwd
RUN ls -l 
RUN ls -l ./target
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

