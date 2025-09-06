FROM eclipse-temurin:17-jdk-alpine as  builder
WORKDIR /app

RUN apk add --no-cache maven

COPY pom.xml /app/
COPY src /app/src/
RUN mvn -f ./pom.xml clean package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /app/target/ots-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar", "--server.port=${PORT:-8080}"]