package com.github.aleksikangas.backend.domain.snapshot;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

/**
 * A snapshot of heat-pump control state.
 *
 * @see HeatPumpSnapshot
 */
@AllArgsConstructor
@Embeddable
@Getter
@Immutable
@NoArgsConstructor
public final class ControlSnapshot implements Serializable {

  @Column(updatable = false)
  private int activeHeatDistributionCircuitCount;
  @Column(updatable = false)
  private boolean compressorActive;
}
