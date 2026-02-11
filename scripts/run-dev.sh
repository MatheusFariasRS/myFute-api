#!/usr/bin/env bash
set -a
source ../.env.development
set +a

cd ..
./mvnw spring-boot:run
