/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.controller.heatpump.temperature;

import com.github.aleksikangas.backend.domain.snapshot.TemperatureSnapshot;
import com.github.aleksikangas.backend.heatpump.client.HeatPumpClient;
import com.github.aleksikangas.backend.heatpump.client.HeatPumpClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("heat-pump/temperatures")
@RestController
public final class HeatPumpTemperatureController {

  private final HeatPumpClient heatPumpClient;

  public HeatPumpTemperatureController(@Autowired final HeatPumpClient heatPumpClient) {
    this.heatPumpClient = heatPumpClient;
  }

  @GetMapping
  public ResponseEntity<TemperatureSnapshot> getTemperatures() {
    try {
      return ResponseEntity.ok(heatPumpClient.readTemperatureSnapshot());
    } catch (final HeatPumpClientException e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
