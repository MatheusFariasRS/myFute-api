package com.myfute.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StatusControllerV1Test {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void statusShouldReturnDatabaseInfo() throws Exception {

    ResultActions result = mockMvc.perform(get("/v1/status").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$.dependencies").isMap())
        .andExpect(jsonPath("$.dependencies.database").isMap()).andExpect(jsonPath("$.updated_at").exists())
        .andExpect(jsonPath("$.dependencies.database.version").value("16.11"))
        .andExpect(jsonPath("$.dependencies.database.max_connections").value(greaterThan(0)))
        .andExpect(jsonPath("$.dependencies.database.opened_connections").isNumber())
        .andExpect(jsonPath("$.dependencies.database.opened_connections").value(greaterThanOrEqualTo(0)))
        .andDo(print());

    result.andExpect(result1 -> assertDoesNotThrow(() -> Instant
        .parse(new ObjectMapper().readTree(result1.getResponse().getContentAsString()).get("updated_at").asText())));
  }
}
