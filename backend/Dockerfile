# --- Stage 1: Build (Maven + JDK 21) ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# 1. Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Copy source and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# --- Stage 2: Runtime (JRE 21) ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 3. Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# 4. Render dynamic port (Render always injects a PORT variable)
ENV PORT=8080
EXPOSE 8080

# 5. Start the app
# -Dserver.address=0.0.0.0 is a requirment for Render to route traffic
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-Dserver.address=0.0.0.0", "-Dspring.profiles.active=production", "-jar", "app.jar"]