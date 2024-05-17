FROM azul/zulu-openjdk:17.0.11

WORKDIR /app

COPY target/*.jar /app/mk-api.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "mk-api.jar"]