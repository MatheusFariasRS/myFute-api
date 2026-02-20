package com.myfute.api.controllersv1;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MigrationsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Flyway flyway;

  @BeforeEach
  void cleanDatabase() {
    flyway.clean();
  }

  @Test
  public void shouldListPendingMigrationsWhenDatabaseIsClean() throws Exception {

    mockMvc.perform(get("/v1/migrations").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$.length()").value(1)) // deve existir 1 migration
                                                                                       // pendente
        .andExpect(jsonPath("$[0].version").exists()).andExpect(jsonPath("$[0].state").value("PENDING"));
  }

  @Test
  public void shouldExecuteMigrationAndThenHaveNoPending() throws Exception {

    // Executa migration
    mockMvc.perform(post("/v1/migrations").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$.executed").isArray()).andExpect(jsonPath("$.executed.length()").value(1))
        .andExpect(jsonPath("$.target").exists());

    // não deve mais ter pendentes
    mockMvc.perform(get("/v1/migrations").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }
}