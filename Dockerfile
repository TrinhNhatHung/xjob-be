# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
#
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:resolve
#
#COPY src ./src
#
#CMD ["./mvnw", "spring-boot:run"]
COPY src/main/resources/redisson.yml /app/resources/
COPY target/xjob-be-1.0-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "xjob-be-1.0-SNAPSHOT.jar"]