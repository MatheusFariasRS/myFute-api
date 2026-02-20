package com.myfute.api.controllersv1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StatusControllerV1Test {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void statusShouldReturnDatabaseInfo() throws Exception {

    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/status").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.dependencies").isMap())
        .andExpect(MockMvcResultMatchers.jsonPath("$.dependencies.database").isMap())
        .andExpect(MockMvcResultMatchers.jsonPath("$.updated_at").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.dependencies.database.version").isString())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.dependencies.database.max_connections").value(Matchers.greaterThan(0)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.dependencies.database.opened_connections").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.dependencies.database.opened_connections")
            .value(Matchers.greaterThanOrEqualTo(0)))
        .andDo(MockMvcResultHandlers.print());

    result.andExpect(result1 -> Assertions.assertDoesNotThrow(() -> Instant
        .parse(new ObjectMapper().readTree(result1.getResponse().getContentAsString()).get("updated_at").asText())));
  }
}
