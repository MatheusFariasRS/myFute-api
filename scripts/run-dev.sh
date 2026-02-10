#!/usr/bin/env bash
set -a
source ../.env
set +a

cd ..
./mvnw spring-boot:run
