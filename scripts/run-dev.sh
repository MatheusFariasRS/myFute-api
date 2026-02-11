#!/usr/bin/env bash
set -e

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

set -a
source "$ROOT_DIR/.env.development"
set +a

cd "$ROOT_DIR"
./mvnw spring-boot:run
