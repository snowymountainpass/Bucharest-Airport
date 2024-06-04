# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the Gradle wrapper and build files
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

# Copy the source code
COPY src/ src/

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Expose the backend port
EXPOSE 8080

# Add a volume for the source code
VOLUME /app/src

# Command to build and run the application
CMD ["./gradlew", "bootRun"]
