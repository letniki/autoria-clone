FROM openjdk:17-alpine

MAINTAINER Letniki

RUN apk add bash

RUN mkdir /app
WORKDIR /app