FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/british-spoken-time-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-XX:+UseZGC","-XX:MaxRAMPercentage=75","-jar","/app/app.jar"]
