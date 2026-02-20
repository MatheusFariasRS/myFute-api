package com.myfute.api.controllers;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/migrations")
public class MigrationsControllerV1 {

  private final Flyway flyway;

  @Value("${feature.migrations:false}")
  private boolean migrationsEnabled;

  public MigrationsControllerV1(Flyway flyway) {
    this.flyway = flyway;
  }

  private ResponseEntity<?> forbiddenIfDisabled() {
    if (!migrationsEnabled) {
      return ResponseEntity.status(403).body(Map.of("error", "Forbidden", "message", "Migrations feature is disabled"));
    }
    return null;
  }

  @GetMapping
  public ResponseEntity<?> getMigration() {

    ResponseEntity<?> forbidden = forbiddenIfDisabled();
    if (forbidden != null)
      return forbidden;

    List<Map<String, Object>> list = new ArrayList<>();

    for (MigrationInfo m : flyway.info().pending()) {
      list.add(Map.of("version", m.getVersion() != null ? m.getVersion().toString() : "", "description",
          m.getDescription(), "script", m.getScript(), "state", m.getState().name()));
    }

    return ResponseEntity.ok(list);
  }

  @PostMapping
  public ResponseEntity<?> runMigration() {

    ResponseEntity<?> forbidden = forbiddenIfDisabled();
    if (forbidden != null)
      return forbidden;

    MigrateResult result = flyway.migrate();

    List<Map<String, Object>> executed = new ArrayList<>();

    result.migrations.forEach(m -> executed
        .add(Map.of("version", m.version != null ? m.version.toString() : "", "description", m.description)));

    return ResponseEntity.ok(Map.of("executed", executed, "target",
        result.targetSchemaVersion != null ? result.targetSchemaVersion.toString() : ""));
  }
}