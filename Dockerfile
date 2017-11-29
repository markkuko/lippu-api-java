FROM openjdk:8-jdk-alpine
ENV LIPPU_HOME=/home/lippu
MAINTAINER Markku Korkeala

ARG jar="lippu-service.jar"

ENV JARFILE=$jar

RUN adduser -S lippu
USER lippu
WORKDIR $LIPPU_HOME

COPY ./artifacts/$jar $LIPPU_HOME/$jar

ENTRYPOINT java -jar $LIPPU_HOME/$JARFILE