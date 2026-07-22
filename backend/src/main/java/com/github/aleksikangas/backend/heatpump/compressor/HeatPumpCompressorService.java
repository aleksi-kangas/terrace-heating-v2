/*
 * Copyright (c) 2026 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.compressor;

import com.github.aleksikangas.backend.domain.compressor.CompressorDutyCycle;
import com.github.aleksikangas.backend.persistence.repositories.HeatPumpSnapshotRepository;
import com.google.common.base.Preconditions;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class HeatPumpCompressorService {

  private final HeatPumpSnapshotRepository heatPumpSnapshotRepository;

  public HeatPumpCompressorService(@Autowired final HeatPumpSnapshotRepository heatPumpSnapshotRepository) {
    this.heatPumpSnapshotRepository = Objects.requireNonNull(heatPumpSnapshotRepository);
  }

  public List<CompressorDutyCycle> getDutyCycles(final Instant startTime, final Instant endTime,
      final Duration resolution) {
    Preconditions.checkArgument(startTime.isBefore(endTime), "startTime must be before endTime");
    Objects.requireNonNull(resolution, "resolution cannot be null");
    return heatPumpSnapshotRepository.findDutyCycles(startTime, endTime, toInterval(resolution));
  }

  private static String toInterval(final Duration resolution) {
    final long seconds = resolution.getSeconds();
    if (seconds % 86400 == 0) {
      return (seconds / 86400) + " days";
    }
    if (seconds % 3600 == 0) {
      return (seconds / 3600) + " hours";
    }
    if (seconds % 60 == 0) {
      return (seconds / 60) + " minutes";
    }
    return seconds + " seconds";
  }
}
