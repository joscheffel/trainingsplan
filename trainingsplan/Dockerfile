# Use a separate stage to reduce the final image size
FROM openjdk:23-slim

# Set the working directory
WORKDIR /app

# Copy the built application from the previous stage
COPY /build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
