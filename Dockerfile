# Stage 1: Build the Spring Boot application using Maven
FROM maven:3.9.4-eclipse-temurin-21 AS builder

# Set working directory inside builder container
WORKDIR /app

# Copy the Maven project files to the container
COPY . .

# Build the project and skip tests
RUN mvn clean package -DskipTests

# Stage 2: Run the Spring Boot app using OpenJDK
FROM openjdk:21-jdk

# Set working directory in the runtime container
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/IssueTrackerBackend.jar app.jar

# Expose the port the Spring Boot app runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
