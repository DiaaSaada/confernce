# Use the official OpenJDK 22 image as the base image
FROM openjdk:22-slim

# Set the working directory in the container
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the project files
COPY pom.xml .
COPY src src

# Build the application
RUN mvn clean package -DskipTests

# Expose the port the app runs on
EXPOSE 8080
# Run the application
CMD ["java", "-jar", "target/conference-booking-1.0.0.jar"]