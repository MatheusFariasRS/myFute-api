#!/usr/bin/env bash
set -e

PROFILE=${1:-dev}

echo "Running migrations for profile: $PROFILE"

if [ "$PROFILE" = "dev" ]; then
  ./mvnw flyway:migrate \
    -Dflyway.url=jdbc:postgresql://localhost:5433/local_db \
    -Dflyway.user=local_user \
    -Dflyway.password=local_password

elif [ "$PROFILE" = "prod" ]; then
  ./mvnw flyway:migrate \
    -Dflyway.url=jdbc:postgresql://${PGHOST}:${PGPORT:-5432}/${PGDATABASE}?sslmode=${PGSSLMODE} \
    -Dflyway.user=${PGUSER} \
    -Dflyway.password=${PGPASSWORD}

else
  echo "Unknown profile: $PROFILE"
  exit 1
fi