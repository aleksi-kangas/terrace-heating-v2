/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.domain.snapshot;

import com.github.aleksikangas.backend.persistence.core.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Entity
@Getter
@Immutable
@NoArgsConstructor
@Table(name = "storage_tank_limit_snapshots")
public final class StorageTankLimitSnapshot extends AbstractEntity {

  @Column(updatable = false)
  private float storageTankMinimumC;
  @Column(updatable = false)
  private float storageTankMaximumC;
  @Column(updatable = false)
  private float storageTankMinimumAdjustedC;
  @Column(updatable = false)
  private float storageTankMaximumAdjustedC;
}
