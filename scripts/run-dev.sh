#!/usr/bin/env bash
set -e

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

# Carrega variáveis de ambiente
set -a
source "$ROOT_DIR/.env.development"
set +a

cd "$ROOT_DIR"

# Roda em modo dev com fork habilitado (necessário para DevTools)
./mvnw spring-boot:run \
  -Dspring-boot.run.fork=true \
  -Dspring-boot.run.jvmArguments="-XX:TieredStopAtLevel=1"
