# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper and .mvn directory for dependency management
COPY .mvn ./mvn
COPY mvnw .
COPY pom.xml .

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy the application source code
COPY src ./src

# Package the application
RUN ./mvnw package -DskipTests

# Define the application's entry point
ENTRYPOINT ["java", "-jar", "target/*.jar"]

# Expose the port your Spring Boot application runs on (default is 8080)
EXPOSE 8080
