#!/usr/bin/env bash
set -e

./mvnw test -Dspring.profiles.active=test
