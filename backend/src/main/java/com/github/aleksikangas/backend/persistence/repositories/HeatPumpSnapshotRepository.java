/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.persistence.repositories;

import com.github.aleksikangas.backend.domain.compressor.CompressorDutyCycle;
import com.github.aleksikangas.backend.domain.snapshot.HeatPumpSnapshot;
import jakarta.annotation.Nonnull;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link HeatPumpSnapshot}s.
 */
@Repository
public interface HeatPumpSnapshotRepository extends JpaRepository<HeatPumpSnapshot, Long> {

  List<HeatPumpSnapshot> findByTimestampAfterOrderByTimestamp(@Nonnull Instant after);

  List<HeatPumpSnapshot> findByTimestampBetweenOrderByTimestamp(@Nonnull Instant from, @Nonnull Instant to);

  @Query(value = """
      SELECT
          bucket_start AS startTime,
          bucket_start + CAST(:interval AS interval) AS endTime,
          CASE
              WHEN COUNT(*) = 0 THEN 0.0
              ELSE SUM(CASE WHEN control_compressor_active THEN 1 ELSE 0 END)::double precision
                   / COUNT(*)
          END AS load,
          SUM(CASE WHEN control_compressor_active THEN 1 ELSE 0 END) AS activeCount,
          COUNT(*) AS count
      FROM (
          SELECT
              date_bin(
                  CAST(:interval AS interval),
                  timestamp,
                  TIMESTAMP '1970-01-01'
              ) AS bucket_start,
              control_compressor_active
          FROM heat_pump_snapshots
          WHERE timestamp >= :from
            AND timestamp < :to
      ) s
      GROUP BY bucket_start
      ORDER BY bucket_start;
      """, nativeQuery = true)
  List<CompressorDutyCycle> findDutyCycles(@Param("from") Instant from, @Param("to") Instant to,
      @Param("interval") String interval);
}
