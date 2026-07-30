package com.github.aleksikangas.backend.domain.heating;

import com.google.common.base.Preconditions;
import lombok.Getter;

@Getter
public enum HeatingState {
  INACTIVE(2),
  ACTIVE(3);

  private final short activeHeatDistributionCircuitCount;

  HeatingState(final int activeHeatDistributionCircuitCount) {
    Preconditions.checkArgument(activeHeatDistributionCircuitCount == 2 ||
        activeHeatDistributionCircuitCount == 3);
    this.activeHeatDistributionCircuitCount = (short) activeHeatDistributionCircuitCount;
  }

  public static HeatingState of(final short activeHeatDistributionCircuitCount) {
    return switch (activeHeatDistributionCircuitCount) {
      case 2 -> INACTIVE;
      case 3 -> ACTIVE;
      default -> throw new IllegalArgumentException("Invalid heat distribution circuit count");
    };
  }
}
