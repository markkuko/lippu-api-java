FROM openjdk:8-jdk-alpine

MAINTAINER Markku Korkeala

ARG jar="lippu-service.jar"

ENV JARFILE=$jar

RUN mkdir -p /opt/lippu

WORKDIR /opt/lippu

COPY ./artifacts/$jar /opt/lippu/$jar

CMD java -jar /opt/lippu/$JARFILE