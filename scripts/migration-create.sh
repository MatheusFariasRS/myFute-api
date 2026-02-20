#!/usr/bin/env bash
set -e

if [ -z "$1" ]; then
  read -p "Migration name: " NAME_INPUT
else
  NAME_INPUT="$1"
fi

VERSION=$(date +"%Y%m%d%H%M%S")
NAME=$(echo "$NAME_INPUT" | tr ' ' '_' | tr '[:upper:]' '[:lower:]')
FILE="src/main/resources/db/migration/V${VERSION}__${NAME}.sql"

mkdir -p src/main/resources/db/migration
touch "$FILE"

echo "Created: $FILE"