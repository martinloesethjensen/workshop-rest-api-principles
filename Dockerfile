# ─── Stage 1: Build ───────────────────────────────────────────────────────────
FROM eclipse-temurin:25-jdk-alpine AS builder

WORKDIR /workspace

# Copy dependency descriptors first so Docker can cache the layer
COPY gradlew gradlew.bat settings.gradle build.gradle ./
COPY gradle ./gradle

# Ensure gradlew is executable (the bit can be lost on Windows git clones)
RUN chmod +x gradlew

# Pre-fetch dependencies (fails gracefully if offline later)
RUN ./gradlew dependencies --no-daemon -q || true

# Copy the rest of the source and build the fat jar (skip tests — participants
# run tests themselves after implementing the TODOs)
COPY src ./src
RUN ./gradlew bootJar --no-daemon -x test

# ─── Stage 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
