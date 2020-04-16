FROM openjdk:8-jre-slim

EXPOSE 8080

RUN mkdir /app

COPY build/libs/*.jar  /app/home-assignment-ro.jar
CMD java  -jar /app/home-assignment-ro.jar

