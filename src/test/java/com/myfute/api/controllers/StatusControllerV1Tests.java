package com.myfute.api.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StatusControllerV1Test {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void statusShouldReturnDatabaseInfo() throws Exception {

    mockMvc.perform(get("/v1/status")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("UP"))
        .andExpect(jsonPath("$.service").value("MyFute API ⚽")).andExpect(jsonPath("$.database").exists())
        .andExpect(jsonPath("$.database").value(containsString("local_db")));
  }
}
