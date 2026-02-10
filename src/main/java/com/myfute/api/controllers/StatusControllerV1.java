package com.myfute.api.controllers;

import com.myfute.api.repositories.StatusRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/status")
public class StatusControllerV1 {

  StatusRepository statusRepository;

  public StatusControllerV1(StatusRepository statusRepository) {
    this.statusRepository = statusRepository;
  }

  @GetMapping
  public ResponseEntity<Map<String, String>> getStatus() {
    return ResponseEntity
        .ok(Map.of("status", "UP", "service", "MyFute API ⚽", "database", statusRepository.databaseVersion()));

  }
}
