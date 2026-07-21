package com.github.aleksikangas.backend.domain.snapshot;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import org.springframework.data.annotation.Immutable;

/**
 * A snapshot of heat-pump control state.
 *
 * @see HeatPumpSnapshot
 */
@Embeddable
@Immutable
public record ControlSnapshot(int activeHeatDistributionCircuitCount,
                              boolean compressorActive) implements Serializable {

}
