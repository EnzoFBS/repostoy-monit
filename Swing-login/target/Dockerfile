# syntax=docker/dockerfile:1

FROM openjdk:11
WORKDIR /java-jar
COPY ./Monitotem-1.0-SNAPSHOT-jar-with-dependencies.jar .
ENTRYPOINT [ "java" , "-jar", "Monitotem-1.0-SNAPSHOT-jar-with-dependencies.jar ." ]
