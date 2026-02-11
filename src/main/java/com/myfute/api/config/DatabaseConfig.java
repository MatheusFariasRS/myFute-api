package com.myfute.api.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

  static {
    Dotenv dotenv = Dotenv.configure().filename(".env.development").ignoreIfMissing().load();

    dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
  }

  @Bean
  public DataSource dataSource(Environment env) {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(env.getRequiredProperty("POSTGRES_DRIVER"));
    ds.setUrl(env.getRequiredProperty("POSTGRES_URL"));
    ds.setUsername(env.getRequiredProperty("POSTGRES_USER"));
    ds.setPassword(env.getRequiredProperty("POSTGRES_PASSWORD"));
    return ds;
  }
}
