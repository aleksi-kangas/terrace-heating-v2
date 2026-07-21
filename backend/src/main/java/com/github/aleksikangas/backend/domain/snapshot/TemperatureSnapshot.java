/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.domain.snapshot;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import org.springframework.data.annotation.Immutable;

/**
 * A snapshot of heat-pump temperatures.
 *
 * @see HeatPumpSnapshot
 */
@Embeddable
@Immutable
public record TemperatureSnapshot(float groundCircuitInC,
                                  float groundCircuitOutC,

                                  float heatDistributionCircuit1C,
                                  float heatDistributionCircuit2C,
                                  float heatDistributionCircuit3C,

                                  float hotGas1C,
                                  float hotGas2C,

                                  float indoorC,
                                  float outdoorC,

                                  float lowerStorageTankC,
                                  float upperStorageTankC) implements Serializable {

}
