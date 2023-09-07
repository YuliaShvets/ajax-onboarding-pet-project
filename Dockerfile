# Use the official OpenJDK base image
FROM openjdk:17-jdk-buster

# Set the working directory in the container
WORKDIR /app

# Copy only the Gradle files into the container
COPY build.gradle.kts settings.gradle.kts /app/
COPY gradle /app/gradle

# Copy the Gradle Wrapper files
COPY gradlew gradlew.bat /app/

# Make the Gradle Wrapper scripts executable
RUN chmod +x /app/gradlew

# Copy the source code into the container
COPY src /app/src

# Build the Spring Boot application inside the container
RUN /app/gradlew build

# Expose the port your Spring Boot application runs on
EXPOSE 8080

# Command to run your Spring Boot application
CMD ["java", "-jar", "build/libs/your-spring-boot-app.jar"]
