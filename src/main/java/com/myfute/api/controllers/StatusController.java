package com.myfute.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

  private static final Logger log = LoggerFactory.getLogger(StatusController.class);

  @GetMapping("/status")
  public String status() {
    return "MyFute API is up ⚽";
  }
}
