package com.beyondsoft.obt.common;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

  @GetMapping("/api/health")
  public Map<String, Object> health() {
    return Map.of(
        "ok",
        true,
        "app",
        "Order Booking Tool Spring",
        "partners",
        java.util.List.of("assessment"),
        "runtime",
        "spring");
  }
}
