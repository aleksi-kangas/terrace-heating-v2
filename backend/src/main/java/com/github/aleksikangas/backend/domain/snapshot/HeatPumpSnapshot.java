/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.domain.snapshot;

import com.github.aleksikangas.backend.persistence.core.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.Immutable;

@Entity
@Immutable
@Table(name = "heat_pump_snapshots")
public final class HeatPumpSnapshot extends AbstractEntity {

  @Column(nullable = false, updatable = false)
  private Instant timestampUtc;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private TemperatureSnapshot temperatureSnapshot;
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private StorageTankLimitSnapshot lowerStorageTankLimitSnapshot;
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private StorageTankLimitSnapshot upperStorageTankLimitSnapshot;
}
