/*
 * Copyright (c) 2026 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.compressor;

import com.github.aleksikangas.backend.domain.compressor.CompressorDutyCycle;
import com.github.aleksikangas.backend.domain.snapshot.HeatPumpSnapshot;
import com.github.aleksikangas.backend.persistence.repositories.HeatPumpSnapshotRepository;
import com.google.common.base.Preconditions;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
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

  /**
   * @implNote A naive implementation which assumes consistent data.
   */
  public List<CompressorDutyCycle> getDutyCycles(final Instant startTime, final Instant endTime,
      final Duration bucketSize) {
    Preconditions.checkArgument(startTime.isBefore(endTime), "startTime must be before endTime");
    Objects.requireNonNull(bucketSize, "bucketSize cannot be null");
    final List<HeatPumpSnapshot> snapshots = heatPumpSnapshotRepository.findByTimestampBetweenOrderByTimestamp(
        startTime,
        endTime);
    if (snapshots.isEmpty()) {
      return Collections.emptyList();
    }
    final List<CompressorDutyCycle> dutyCycles = new ArrayList<>();
    Instant bucketStart = startTime;
    int snapshotIndex = 0;
    while (bucketStart.isBefore(endTime)) {
      final Instant bucketEnd = bucketStart.plus(bucketSize);
      long activeCount = 0;
      long totalCount = 0;
      // [bucketStart, bucketEnd)
      while (snapshotIndex < snapshots.size()) {
        final HeatPumpSnapshot snapshot = snapshots.get(snapshotIndex);
        final Instant timestamp = snapshot.getTimestamp();
        if (!timestamp.isBefore(bucketStart) && timestamp.isBefore(bucketEnd)) {
          ++totalCount;
          if (snapshot.getCompressorSnapshot().isActive()) {
            ++activeCount;
          }
          ++snapshotIndex;
        } else if (!timestamp.isBefore(bucketEnd)) {
          break;
        } else {
          ++snapshotIndex;
        }
      }
      if (totalCount > 0) {
        final double load = (double) activeCount / totalCount;
        dutyCycles.add(new CompressorDutyCycle(bucketStart, bucketEnd, load, activeCount, totalCount));
      }
      bucketStart = bucketEnd;
    }
    return dutyCycles;
  }
}
