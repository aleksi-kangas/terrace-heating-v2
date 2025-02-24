/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.snapshot;

import com.github.aleksikangas.backend.domain.snapshot.HeatPumpSnapshot;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * A {@link HeatPumpSnapshot}-event.
 */
@Getter
public final class HeatPumpSnapshotEvent extends ApplicationEvent {

  private final HeatPumpSnapshot heatPumpSnapshot;

  public HeatPumpSnapshotEvent(final Object source, final HeatPumpSnapshot heatPumpSnapshot) {
    super(source);
    this.heatPumpSnapshot = heatPumpSnapshot;
  }
}
