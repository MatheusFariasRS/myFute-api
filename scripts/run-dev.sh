#!/usr/bin/env bash
set -e

./mvnw spring-boot:run \
  -Dspring-boot.run.profiles=dev \
  -Dspring-boot.run.fork=true \
  -Dspring-boot.run.jvmArguments="-XX:TieredStopAtLevel=1"