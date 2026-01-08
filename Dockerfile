# Base image
FROM openjdk:17-jdk-slim

# Working directory
WORKDIR /app

# Copy jar file
COPY target/OnlineEgitimVeSinavSurecleriYonetimSistemi-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
