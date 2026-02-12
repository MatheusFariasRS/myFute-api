.PHONY: dev test docker-up docker-down run watch-test check-watchexec watch

run: docker-up watch

dev:
	./scripts/run-dev.sh

test:
	./scripts/test.sh

docker-up:
	./scripts/docker-up.sh

docker-down:
	./scripts/docker-down.sh

watch: check-watchexec
	watchexec \
  	-w src \
  	-w pom.xml \
  	-e java,yml,yaml,properties,xml \
  	--restart \
  	--clear \
  	-- \
  	./scripts/run-dev.sh

watch-test: check-watchexec
	watchexec \
	-w src \
	-w pom.xml \
	-e java,yml,yaml,properties,xml \
	--clear=clear \
	-- \
	./mvnw test

check-watchexec:
	@command -v watchexec >/dev/null 2>&1 || { \
		echo "❌ watchexec não está instalado."; \
		echo "Baixe o binário em:"; \
		echo "https://github.com/watchexec/watchexec/releases"; \
		exit 1; \
	}
