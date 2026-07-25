# ========================
# Stage: base
# ========================
FROM eclipse-temurin:25-jdk AS base
WORKDIR /app

# ========================
# Stage: dev
# ========================
FROM base AS dev
COPY .. .
RUN chmod +x docker/dev-entrypoint.sh
EXPOSE 8081
CMD ["sh", "docker/dev-entrypoint.sh"]

# ========================
# Stage: prod-builder
# ========================
FROM base AS prod-builder
COPY ../gradlew .
COPY ../gradle gradle/
COPY ../build.gradle.kts .
COPY ../settings.gradle.kts .
COPY ../src src/
RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon
RUN ./gradlew bootJar --no-daemon

# ========================
# Stage: prod
# ========================
FROM eclipse-temurin:25-jre AS prod
WORKDIR /app
COPY --from=prod-builder /app/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
