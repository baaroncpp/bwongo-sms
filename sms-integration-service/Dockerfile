# Use the official Gradle image to build the app
FROM gradle:7.4.2-jdk17 AS build

LABEL maintainer="BkAaron <baaronlubega1@gmail.com>"

# Set the working directory
WORKDIR /app

# Copy Gradle wrapper and settings files first
COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY settings.gradle /app/settings.gradle
COPY build.gradle /app/build.gradle

# Ensure gradlew has executable permissions
RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew build -x test --no-daemon --info --stacktrace || true

# Copy the project files
COPY . .

# Build the project
RUN ./gradlew build --no-daemon

# Use the official OpenJDK image for the runtime
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/build/libs/integration-service.jar app.jar

# Expose the application port
EXPOSE 2028

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
