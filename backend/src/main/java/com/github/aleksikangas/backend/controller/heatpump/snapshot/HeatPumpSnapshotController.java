/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.controller.heatpump.snapshot;

import com.github.aleksikangas.backend.domain.snapshot.HeatPumpSnapshot;
import com.github.aleksikangas.backend.persistence.repositories.HeatPumpSnapshotRepository;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Serves {@link HeatPumpSnapshot}s for specific time intervals.
 */
@RequestMapping("heat-pump/snapshots")
@RestController
public final class HeatPumpSnapshotController {

  private final HeatPumpSnapshotRepository heatPumpSnapshotRepository;

  public HeatPumpSnapshotController(@Autowired final HeatPumpSnapshotRepository heatPumpSnapshotRepository) {
    this.heatPumpSnapshotRepository = heatPumpSnapshotRepository;
  }

  /**
   * Fetches {@link HeatPumpSnapshot}s between two points in time.
   *
   * @param from Start boundary of the query timeline (e.g., 2026-07-10T00:00:00Z)
   * @param to   End boundary of the query timeline
   */
  @GetMapping
  public ResponseEntity<List<HeatPumpSnapshot>> getSnapshotsBetween(
      @DateTimeFormat(iso = ISO.DATE_TIME) @NotNull @RequestParam final Instant from,
      @DateTimeFormat(iso = ISO.DATE_TIME) @NotNull @RequestParam final Instant to) {
    if (from.isAfter(to)) {
      return ResponseEntity.badRequest().build();
    }
    if (Duration.between(from, to).toDays() > 30) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(heatPumpSnapshotRepository.findByTimestampBetweenOrderByTimestamp(from, to));
  }

  /**
   * Fetches {@link HeatPumpSnapshot}s for the given trailing {@code days}.
   *
   * @param days The number of trailing days
   */
  @GetMapping("days/{days}")
  public ResponseEntity<List<HeatPumpSnapshot>> getSnapshotsTrailingDays(
      @PathVariable @Min(1) @Max(30) final int days) {
    final Instant threshold = Instant.now().minus(days, ChronoUnit.DAYS);
    return ResponseEntity.ok(heatPumpSnapshotRepository.findByTimestampAfterOrderByTimestamp(threshold));
  }
}
