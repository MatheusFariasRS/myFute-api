.PHONY: dev test docker-up docker-down run

run: docker-up dev

dev:
	./scripts/run-dev.sh

test:
	./scripts/test.sh

docker-up:
	./scripts/docker-up.sh

docker-down:
	./scripts/docker-down.sh
