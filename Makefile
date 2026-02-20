.PHONY: dev test docker-up docker-down run watch-test check-watchexec watch migration-create migration-up

run: docker-up watch

# Perfil de desenvolvimento
dev:
	./scripts/run-dev.sh

# Perfil de teste
test:
	./mvnw test -Dspring.profiles.active=test

docker-up:
	./scripts/docker-up.sh

docker-down:
	./scripts/docker-down.sh

migration-create:
	./scripts/migration-create.sh $(name)

migration-up:
	./scripts/migration-up.sh

# Watch para dev
watch: check-watchexec
	watchexec \
  	-w src \
  	-w pom.xml \
  	-e java,yml,yaml,properties,xml \
  	--restart \
  	--clear \
  	-- \
  	./scripts/run-dev.sh

# Watch para testes
watch-test: check-watchexec
	watchexec \
  	-w src \
  	-w pom.xml \
  	-e java,yml,yaml,properties,xml \
  	--clear \
  	-- \
  	./mvnw test -Dspring.profiles.active=test $(if $(filter-out $@,$(MAKECMDGOALS)),-Dtest=$(filter-out $@,$(MAKECMDGOALS)),)

%:
	@:

check-watchexec:
	@command -v watchexec >/dev/null 2>&1 || { \
		echo "❌ watchexec não está instalado."; \
		echo "Baixe o binário em:"; \
		echo "https://github.com/watchexec/watchexec/releases"; \
		exit 1; \
	}