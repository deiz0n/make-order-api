FROM ubuntu:latest
LABEL authors="eduar"

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install gradle -v
RUN ./gradlew clean build

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /build/libs/make-order-0.0.1-SNAPSHOT.war app.war

ENTRYPOINT ["java", "-jar", "app.war"]