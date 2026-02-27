#!/usr/bin/env bash
set -e

COMPOSE="docker compose -f infra/compose.yaml"

$COMPOSE up -d --build

CID="$($COMPOSE ps -q database)"

i=0
while [ "$(docker inspect -f '{{.State.Health.Status}}' "$CID")" != "healthy" ]; do
  dots=$(printf "%*s" $((i % 4)) "" | tr ' ' '.')
  printf "\r\033[KWaiting for Postgres (database) to be healthy%s" "$dots"
  i=$((i + 1))
  sleep 0.3
done

printf "\r\033[KPostgres is healthy.\n"