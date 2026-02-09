package com.myfute.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/v1/status")
public class StatusControllerV1 {

  @GetMapping
  public ResponseEntity<Map<String, String>> getStatus() {
    return ResponseEntity.ok(Map.of("status", "UP", "service", "MyFute API ⚽"));
  }
}
