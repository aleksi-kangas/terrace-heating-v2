/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.snapshot;

import com.github.aleksikangas.backend.persistence.repositories.HeatPumpSnapshotRepository;
import com.google.errorprone.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Persists {@link com.github.aleksikangas.backend.domain.snapshot.HeatPumpSnapshot}s upon
 * {@link HeatPumpSnapshotEvent}s.
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
@ThreadSafe
public final class HeatPumpSnapshotPersister {

  private static final Logger LOG = LoggerFactory.getLogger(HeatPumpSnapshotPersister.class);

  private final HeatPumpSnapshotRepository heatPumpSnapshotRepository;

  public HeatPumpSnapshotPersister(@Autowired final HeatPumpSnapshotRepository heatPumpSnapshotRepository) {
    this.heatPumpSnapshotRepository = heatPumpSnapshotRepository;
  }

  @Async
  @EventListener
  public void onHeatPumpSnapshotEvent(final HeatPumpSnapshotEvent event) {
    LOG.debug("Persisting HeatPumpSnapshot={} from HeatPumpSnapshotEvent={}", event.getHeatPumpSnapshot(), event);
    heatPumpSnapshotRepository.save(event.getHeatPumpSnapshot());
  }
}
