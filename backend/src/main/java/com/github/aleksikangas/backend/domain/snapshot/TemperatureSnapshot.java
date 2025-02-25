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
 * A snapshot of heat-pump temperatures.
 *
 * @see HeatPumpSnapshot
 */
@AllArgsConstructor
@Entity
@Getter
@Immutable
@NoArgsConstructor
@Table(name = "temperature_snapshots")
public final class TemperatureSnapshot extends AbstractEntity {

  @Column(updatable = false)
  private float groundCircuitInC;
  @Column(updatable = false)
  private float groundCircuitOutC;

  @Column(updatable = false)
  private float heatDistributionCircuit1C;
  @Column(updatable = false)
  private float heatDistributionCircuit2C;
  @Column(updatable = false)
  private float heatDistributionCircuit3C;

  @Column(updatable = false)
  private float hotGas1C;
  @Column(updatable = false)
  private float hotGas2C;

  @Column(updatable = false)
  private float indoorC;
  @Column(updatable = false)
  private float outdoorC;

  @Column(updatable = false)
  private float lowerStorageTankC;
  @Column(updatable = false)
  private float upperStorageTankC;
}
