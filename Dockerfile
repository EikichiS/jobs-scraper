# Etapa de build
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn -U -B -DskipTests clean package

# Etapa final
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# copiamos el jar final
COPY --from=build /app/target/*-jar-with-dependencies.jar app.jar

# run
ENTRYPOINT ["java", "-jar", "/app/app.jar"]