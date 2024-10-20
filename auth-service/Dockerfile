# Use a base image with JDK
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container (make sure to adjust the name)
COPY target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar

# Expose the port the application runs on
EXPOSE 8085

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "auth-service.jar"]
