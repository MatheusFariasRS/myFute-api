package com.myfute.api.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatusControllerV1.class)
class StatusControllerV1Test {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void statusShouldReturnOk() throws Exception {

    ResultActions result = mockMvc.perform(get("/v1/status"));

    result.andExpect(status().isOk());
  }
}
