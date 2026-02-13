package com.myfute.api.repositories;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Repository
public class StatusRepository {

  private final Dotenv dotenv;

  public StatusRepository() {
    this.dotenv = Dotenv.configure().filename(".env.development").ignoreIfMissing().load();
  }

  public Connection getNewConnection() throws SQLException {
    String host = dotenv.get("PGHOST");
    String database = dotenv.get("PGDATABASE");
    String user = dotenv.get("PGUSER");
    String password = dotenv.get("PGPASSWORD");
    String sslMode = dotenv.get("PGSSLMODE");

    String url = "jdbc:postgresql://" + host + "/" + database + "?sslmode=" + sslMode;

    return DriverManager.getConnection(url, user, password);

  }

  public String getDatabaseName() {
    return dotenv.get("PGDATABASE");
  }
}
