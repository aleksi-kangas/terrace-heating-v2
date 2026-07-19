/*
 * Copyright (c) 2026 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.controller.heatpump.compressor;

import com.github.aleksikangas.backend.domain.compressor.CompressorDutyCycle;
import com.github.aleksikangas.backend.heatpump.compressor.HeatPumpCompressorService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("heat-pump/compressor")
@RestController
public final class HeatPumpCompressorController {

  private final HeatPumpCompressorService heatPumpCompressorService;

  public HeatPumpCompressorController(@Autowired final HeatPumpCompressorService heatPumpCompressorService) {
    this.heatPumpCompressorService = Objects.requireNonNull(heatPumpCompressorService);
  }

  /**
   * Fetches compressor duty cycle statistics between two points in time.
   *
   * @param from       Start boundary of the query timeline (e.g., 2026-07-10T00:00:00Z)
   * @param to         End boundary of the query timeline
   * @param resolution The window step resolution (e.g., PT1H for hourly, P1D for daily)
   */
  @GetMapping
  public ResponseEntity<List<CompressorDutyCycle>> getDutyCyclesBetween(
      @RequestParam("from") final Instant from,
      @RequestParam("to") final Instant to,
      @RequestParam(value = "resolution", defaultValue = "PT1H") final Duration resolution) {
    final List<CompressorDutyCycle> dutyCycles = heatPumpCompressorService.getDutyCycles(from, to, resolution);
    return ResponseEntity.ok(dutyCycles);
  }

  /**
   * Fetches {@link CompressorDutyCycle} statistics for the given trailing {@code days}.
   *
   * @param days       The number of trailing days
   * @param resolution The window step resolution (e.g., PT1H for hourly, P1D for daily)
   */
  @GetMapping("days/{days}")
  public ResponseEntity<List<CompressorDutyCycle>> getDutyCyclesTrailingDays(
      @PathVariable @Min(1) @Max(30) final int days,
      @RequestParam(value = "resolution", defaultValue = "PT1H") final Duration resolution) {
    final Instant threshold = Instant.now().minus(days, ChronoUnit.DAYS);
    final List<CompressorDutyCycle> dutyCycles = heatPumpCompressorService.getDutyCycles(threshold, Instant.now(),
        resolution);
    return ResponseEntity.ok(dutyCycles);
  }
}
