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
    return DriverManager.getConnection(dotenv.get("POSTGRES_URL"), dotenv.get("POSTGRES_USER"),
        dotenv.get("POSTGRES_PASSWORD"));
  }

  public String getDatabaseName() {
    return dotenv.get("POSTGRES_DB");
  }
}
