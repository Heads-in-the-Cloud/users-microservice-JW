FROM openjdk:11-jre-slim
FROM 
RUN mvn package
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

