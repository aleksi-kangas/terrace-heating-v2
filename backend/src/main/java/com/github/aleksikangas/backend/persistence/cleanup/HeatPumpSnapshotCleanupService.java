package com.github.aleksikangas.backend.persistence.cleanup;

import com.github.aleksikangas.backend.persistence.repositories.HeatPumpSnapshotRepository;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HeatPumpSnapshotCleanupService {

  private static final Logger LOG = LoggerFactory.getLogger(HeatPumpSnapshotCleanupService.class);

  private final HeatPumpSnapshotRepository heatPumpSnapshotRepository;

  @Value("${heat-pump.retention.snapshot-retention:30d}")
  private Duration snapshotRetention;

  public HeatPumpSnapshotCleanupService(@Autowired final HeatPumpSnapshotRepository heatPumpSnapshotRepository) {
    this.heatPumpSnapshotRepository = Objects.requireNonNull(heatPumpSnapshotRepository);
  }

  @Transactional
  @Scheduled(cron = "0 0 0 * * *")  // Every day at 00:00
  public void cleanupOldSnapshots() {
    final Instant threshold = Instant.now().minus(snapshotRetention);
    if (heatPumpSnapshotRepository.deleteAllBefore(threshold) > 0) {
      LOG.info("Deleted heat pump snapshots older than {}", threshold);
    }
  }
}
