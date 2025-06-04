# Etapa 1: build do jar
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: imagem final com o JAR
FROM eclipse-temurin:21-jdk-alpine
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY --from=build /app/${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]