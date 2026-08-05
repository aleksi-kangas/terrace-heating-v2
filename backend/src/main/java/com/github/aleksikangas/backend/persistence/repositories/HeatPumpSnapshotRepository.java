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
import org.springframework.data.jpa.repository.Modifying;
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
          b.bucket_start AS startTime,
          b.bucket_start + CAST(:interval AS interval) AS endTime,
          COALESCE(
              AVG(CASE WHEN s.control_compressor_active THEN 1.0 ELSE 0.0 END),
              0.0
          )::double precision AS load,
          COALESCE(
              SUM(CASE WHEN s.control_compressor_active THEN 1 ELSE 0 END),
              0
          ) AS activeCount,
          COUNT(s.timestamp) AS count
      FROM generate_series(
          CAST(:from AS timestamptz),
          CAST(:to AS timestamptz) - CAST(:interval AS interval),
          CAST(:interval AS interval)
      ) AS b(bucket_start)
      LEFT JOIN heat_pump_snapshots s
          ON s.timestamp >= b.bucket_start
         AND s.timestamp < b.bucket_start + CAST(:interval AS interval)
      GROUP BY b.bucket_start
      ORDER BY b.bucket_start;
      """, nativeQuery = true)
  List<CompressorDutyCycle> findDutyCycles(@Param("from") Instant from, @Param("to") Instant to,
      @Param("interval") String interval);

  @Modifying
  @Query(value = """
      DELETE FROM heat_pump_snapshots
      WHERE timestamp < :threshold
      """, nativeQuery = true)
  int deleteAllBefore(@Param("threshold") Instant threshold);
}
