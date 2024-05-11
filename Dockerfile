FROM azul/zulu-openjdk:17.0.11

WORKDIR /app

COPY target/*.jar /app/mk-api.jar

EXPOSE 8080

CMD ["java", "-jar", "mk-api.jar"]