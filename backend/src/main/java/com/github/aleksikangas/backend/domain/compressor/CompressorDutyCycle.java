/*
 * Copyright (c) 2026 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.domain.compressor;

import com.google.common.base.Preconditions;
import java.time.Instant;

/**
 * Represents the aggregated load metrics and performance statistics of a heat pump compressor over a distinct, fixed
 * time window.
 *
 * @param startTime   The inclusive lower time bound of the evaluation window.
 * @param endTime     The exclusive upper time bound of the evaluation window.
 * @param load        The normalized operational load factor of the compressor within the window, represented as a
 *                    fractional percentage between {@code 0.0} (entirely idle) and {@code 1.0} (fully active).
 *                    Calculated as {@code activeCount / count}.
 * @param activeCount The total number of state observations within this window where the compressor was actively
 *                    running ({@code active == true}).
 * @param count       The gross number of recorded state observation snapshots captured within this window timeframe.
 */
public record CompressorDutyCycle(Instant startTime,
                                  Instant endTime,
                                  double load,
                                  long activeCount,
                                  long count) {

  public CompressorDutyCycle {
    Preconditions.checkArgument(startTime.isBefore(endTime));
    Preconditions.checkArgument(0 <= load && load <= 1);
    Preconditions.checkArgument(activeCount >= 0);
    Preconditions.checkArgument(count >= 0);
  }
}
