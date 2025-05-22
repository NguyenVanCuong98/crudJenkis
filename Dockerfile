# 1. Build project bằng Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml trước để resolve dependency trước (tối ưu cache)
COPY pom.xml .

# Copy src để build
COPY src ./src

# Build và tạo file .jar (nếu lỗi, thường do project)
RUN mvn clean package -DskipTests

# 2. Dùng image nhẹ để chạy app
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy file jar từ stage build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Chạy app
ENTRYPOINT ["java", "-jar", "app.jar"]
