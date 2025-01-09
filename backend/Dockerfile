FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean install -X -DskipTests=true
FROM openjdk:21
COPY --from=builder /app/target/backend-0.0.1-SNAPSHOT.jar springDocker.jar
ENTRYPOINT ["java", "-jar", "springDocker.jar"]
