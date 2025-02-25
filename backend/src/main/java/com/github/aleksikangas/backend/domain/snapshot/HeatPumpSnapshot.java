/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.domain.snapshot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.aleksikangas.backend.persistence.core.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

/**
 * A snapshot of heat-pump state at a point in time.
 *
 * @see StorageTankLimitSnapshot
 * @see TemperatureSnapshot
 */
@AllArgsConstructor
@Entity
@Immutable
@NoArgsConstructor
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

  /**
   * JSON-constructor from MQTT-broker subscription.
   *
   * @param timestampEpochS                  timestamp of the snapshot as seconds from epoch
   * @param groundCircuitInC                 temperature of the ground circuit input, in Celsius
   * @param groundCircuitOutC                temperature of the ground circuit output, in Celsius
   * @param heatDistributionCircuit1C        temperature of the heat distribution circuit 1, in Celsius
   * @param heatDistributionCircuit2C        temperature of the heat distribution circuit 2, in Celsius
   * @param heatDistributionCircuit3C        temperature of the heat distribution circuit 3, in Celsius
   * @param hotGas1C                         temperature of the hot gas 1, in Celsius
   * @param hotGas2C                         temperature of the hot gas 2, in Celsius
   * @param indoorC                          indoor temperature, in Celsius
   * @param outdoorC                         outdoor temperature, in Celsius
   * @param lowerStorageTankC                temperature of the lower storage tank, in Celsius
   * @param upperStorageTankC                temperature of the upper storage tank, in Celsius
   * @param lowerStorageTankMinimumC         minimum threshold value of the lower storage tank
   * @param lowerStorageTankMaximumC         maximum threshold value of the lower storage tank
   * @param lowerStorageTankMinimumAdjustedC adjusted (= corrections applied) minimum threshold value of the lower
   *                                         storage tank
   * @param lowerStorageTankMaximumAdjustedC adjusted (= corrections applied) maximum threshold value of the lower
   *                                         storage tank
   * @param upperStorageTankMinimumC         minimum threshold value of the upper storage tank
   * @param upperStorageTankMaximumC         maximum threshold value of the upper storage tank
   * @param upperStorageTankMinimumAdjustedC adjusted (= corrections applied) minimum threshold value of the upper
   *                                         storage tank
   * @param upperStorageTankMaximumAdjustedC adjusted (= corrections applied) maximum threshold value of the upper
   *                                         storage tank
   */
  @JsonCreator
  public HeatPumpSnapshot(
      @JsonProperty("timestamp") final long timestampEpochS,

      // TemperatureSnapshot
      @JsonProperty("ground_circuit_in_c") final float groundCircuitInC,
      @JsonProperty("ground_circuit_out_c") final float groundCircuitOutC,

      @JsonProperty("heat_distribution_circuit_1_c") final float heatDistributionCircuit1C,
      @JsonProperty("heat_distribution_circuit_2_c") final float heatDistributionCircuit2C,
      @JsonProperty("heat_distribution_circuit_3_c") final float heatDistributionCircuit3C,

      @JsonProperty("hot_gas_1_c") final float hotGas1C,
      @JsonProperty("hot_gas_2_c") final float hotGas2C,

      @JsonProperty("indoor_c") final float indoorC,
      @JsonProperty("outdoor_c") final float outdoorC,

      @JsonProperty("lower_storage_tank_c") final float lowerStorageTankC,
      @JsonProperty("upper_storage_tank_c") final float upperStorageTankC,

      // Lower StorageTankLimitSnapshot
      @JsonProperty("lower_storage_tank_minimum_c") final float lowerStorageTankMinimumC,
      @JsonProperty("lower_storage_tank_maximum_c") final float lowerStorageTankMaximumC,
      @JsonProperty("lower_storage_tank_minimum_adjusted_c") final float lowerStorageTankMinimumAdjustedC,
      @JsonProperty("lower_storage_tank_maximum_adjusted_c") final float lowerStorageTankMaximumAdjustedC,

      // Upper StorageTankLimitSnapshot
      @JsonProperty("upper_storage_tank_minimum_c") final float upperStorageTankMinimumC,
      @JsonProperty("upper_storage_tank_maximum_c") final float upperStorageTankMaximumC,
      @JsonProperty("upper_storage_tank_minimum_adjusted_c") final float upperStorageTankMinimumAdjustedC,
      @JsonProperty("upper_storage_tank_maximum_adjusted_c") final float upperStorageTankMaximumAdjustedC) {
    this(Instant.ofEpochSecond(timestampEpochS),
        new TemperatureSnapshot(
            groundCircuitInC,
            groundCircuitOutC,
            heatDistributionCircuit1C,
            heatDistributionCircuit2C,
            heatDistributionCircuit3C,
            hotGas1C,
            hotGas2C,
            indoorC,
            outdoorC,
            lowerStorageTankC,
            upperStorageTankC),
        new StorageTankLimitSnapshot(
            lowerStorageTankMinimumC,
            lowerStorageTankMaximumC,
            lowerStorageTankMinimumAdjustedC,
            lowerStorageTankMaximumAdjustedC
        ),
        new StorageTankLimitSnapshot(
            upperStorageTankMinimumC,
            upperStorageTankMaximumC,
            upperStorageTankMinimumAdjustedC,
            upperStorageTankMaximumAdjustedC
        ));
  }
}
