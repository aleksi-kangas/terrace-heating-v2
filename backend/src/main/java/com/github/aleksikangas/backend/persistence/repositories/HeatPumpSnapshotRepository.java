/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.persistence.repositories;

import com.github.aleksikangas.backend.domain.snapshot.HeatPumpSnapshot;
import jakarta.annotation.Nonnull;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link HeatPumpSnapshot}s.
 */
@Repository
public interface HeatPumpSnapshotRepository extends JpaRepository<HeatPumpSnapshot, Long> {

  List<HeatPumpSnapshot> findByTimestampAfterOrderByTimestamp(@Nonnull Instant after);

  List<HeatPumpSnapshot> findByTimestampBetweenOrderByTimestamp(@Nonnull Instant from, @Nonnull Instant to);
}
