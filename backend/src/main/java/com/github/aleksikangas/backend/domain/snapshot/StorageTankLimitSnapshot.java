/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.domain.snapshot;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

/**
 * A snapshot of heat-pump storage tank limits, which guide the heat-pump run operations.
 *
 * @see HeatPumpSnapshot
 */
@AllArgsConstructor
@Embeddable
@Getter
@Immutable
@NoArgsConstructor
public final class StorageTankLimitSnapshot implements Serializable {

  @Column(updatable = false)
  private float storageTankMinimumC;
  @Column(updatable = false)
  private float storageTankMaximumC;
  @Column(updatable = false)
  private float storageTankMinimumAdjustedC;
  @Column(updatable = false)
  private float storageTankMaximumAdjustedC;
}
