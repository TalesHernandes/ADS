# Use a imagem base do Maven para compilar o projeto
FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app

# Copie o arquivo pom.xml e as dependências do projeto
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copie o código-fonte do projeto
COPY src ./src

# Compile o projeto
RUN mvn clean package -DskipTests

# Use uma imagem base do OpenJDK para rodar o aplicativo
FROM openjdk:11-jre-slim
WORKDIR /app

# Copie o arquivo JAR do estágio de build
COPY --from=build /app/target/ProjetoFunc-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta que o Spring Boot usa
EXPOSE 8080

# Comando para rodar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]