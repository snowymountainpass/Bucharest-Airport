# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /build

# Copy the Gradle wrapper and build files
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

# Copy the source code
COPY src/ src/

# Make the Gradle wrapper executable and build the application
RUN chmod +x gradlew
RUN ./gradlew build

# Use a lighter image for the final run stage
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /build/build/libs/*.jar backend-0.0.1-SNAPSHOT.jar

# Expose the backend port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]