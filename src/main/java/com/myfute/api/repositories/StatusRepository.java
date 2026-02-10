package com.myfute.api.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StatusRepository {

  private final JdbcTemplate jdbcTemplate;

  public StatusRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public String databaseVersion() {
    return jdbcTemplate.queryForObject("select current_database()", String.class);
  }

}
