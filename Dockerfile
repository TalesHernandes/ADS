#
# Build stage
#
FROM maven:3.8.2-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080
ENTRYPOINT [ "java","-jar","app.jar" ]