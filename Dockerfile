# Stage 1: Build
FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

## Stage 2: Run
#FROM openjdk:latest
#WORKDIR /app
#COPY --from=build /app/target/subscribr-*.jar app.jar
#RUN ls -al /app
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ./target/subscribr-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
