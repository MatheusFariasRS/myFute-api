package com.myfute.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RestController
@RequestMapping("/v1/status")
public class StatusControllerV1 {

  private final DataSource dataSource;

  public StatusControllerV1(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @GetMapping
  public ResponseEntity<Map<String, Object>> getStatus() throws SQLException {

    Instant updatedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);

    try (Connection conn = dataSource.getConnection()) {

      String version;
      try (PreparedStatement ps = conn.prepareStatement("SHOW server_version;"); ResultSet rs = ps.executeQuery()) {
        rs.next();
        version = rs.getString(1);
      }

      int maxConnections;
      try (PreparedStatement ps = conn.prepareStatement("SHOW max_connections;"); ResultSet rs = ps.executeQuery()) {
        rs.next();
        maxConnections = rs.getInt(1);
      }

      int openedConnections;
      try (
          PreparedStatement ps = conn
              .prepareStatement("SELECT COUNT(*) FROM pg_stat_activity WHERE datname = current_catalog;");
          ResultSet rs = ps.executeQuery()) {
        rs.next();
        openedConnections = rs.getInt(1);
      }

      return ResponseEntity.ok(Map.of("updated_at", updatedAt, "dependencies", Map.of("database",
          Map.of("version", version, "max_connections", maxConnections, "opened_connections", openedConnections))));
    }
  }
}
