package com.myfute.api.repositories;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class StatusRepository {

  private Connection getNewConnection() throws SQLException {
    Dotenv dotenv = Dotenv.configure()
      .filename(".env.development")
      .ignoreIfMissing()
      .load();

    return DriverManager.getConnection(
      dotenv.get("POSTGRES_URL"),
      dotenv.get("POSTGRES_USER"),
      dotenv.get("POSTGRES_PASSWORD")
    );


  }

  public String databaseVersion() {
    try (Connection conn = getNewConnection();
         PreparedStatement ps = conn.prepareStatement("SELECT current_database()");
         ResultSet rs = ps.executeQuery()) {
      rs.next();
      return rs.getString(1);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

}
