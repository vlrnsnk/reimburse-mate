# Use a JDK image to build the application
FROM eclipse-temurin:17-jdk-alpine AS build

# Specify author
LABEL authors="vlrnsnk"

# Install Maven
RUN apk add --no-cache maven

# Set the working directory
WORKDIR /app

# Copy the source code
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use a JRE image to run the application (smaller size)
FROM eclipse-temurin:17-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the built application
COPY --from=build /app/target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
