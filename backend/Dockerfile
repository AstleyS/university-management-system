FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# copy the built JAR
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]