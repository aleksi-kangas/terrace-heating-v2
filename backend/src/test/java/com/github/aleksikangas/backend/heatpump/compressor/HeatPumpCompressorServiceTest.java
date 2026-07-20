package com.github.aleksikangas.backend.heatpump.compressor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.github.aleksikangas.backend.domain.compressor.CompressorDutyCycle;
import com.github.aleksikangas.backend.domain.snapshot.ControlSnapshot;
import com.github.aleksikangas.backend.domain.snapshot.HeatPumpSnapshot;
import com.github.aleksikangas.backend.persistence.repositories.HeatPumpSnapshotRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HeatPumpCompressorServiceTest {

  @Mock
  private HeatPumpSnapshotRepository repository;

  @InjectMocks
  private HeatPumpCompressorService service;

  @Test
  void shouldReturnEmptyBucketsWhenNoSnapshots() {
    Instant start = Instant.parse("2026-01-01T00:00:00Z");
    Instant end = start.plus(Duration.ofMinutes(5));

    when(repository.findByTimestampBetweenOrderByTimestamp(start, end))
        .thenReturn(List.of());

    List<CompressorDutyCycle> cycles =
        service.getDutyCycles(start, end, Duration.ofMinutes(1));

    assertThat(cycles).hasSize(5);

    for (int i = 0; i < 5; ++i) {
      CompressorDutyCycle cycle = cycles.get(i);

      assertThat(cycle.startTime())
          .isEqualTo(start.plus(Duration.ofMinutes(i)));
      assertThat(cycle.endTime())
          .isEqualTo(start.plus(Duration.ofMinutes(i + 1)));
      assertThat(cycle.load()).isZero();
      assertThat(cycle.activeCount()).isZero();
      assertThat(cycle.count()).isZero();
    }
  }

  @Test
  void shouldReturnZeroDutyCycle() {
    Instant start = Instant.parse("2026-01-01T00:00:00Z");
    Instant end = start.plus(Duration.ofMinutes(1));

    when(repository.findByTimestampBetweenOrderByTimestamp(start, end))
        .thenReturn(List.of(
            snapshot(start, false),
            snapshot(start.plusSeconds(15), false),
            snapshot(start.plusSeconds(30), false),
            snapshot(start.plusSeconds(45), false)));

    CompressorDutyCycle cycle =
        service.getDutyCycles(start, end, Duration.ofMinutes(1)).getFirst();

    assertThat(cycle.load()).isEqualTo(0.0);
    assertThat(cycle.activeCount()).isZero();
    assertThat(cycle.count()).isEqualTo(4);
  }

  @Test
  void shouldReturnFullDutyCycle() {
    Instant start = Instant.parse("2026-01-01T00:00:00Z");
    Instant end = start.plus(Duration.ofMinutes(1));

    when(repository.findByTimestampBetweenOrderByTimestamp(start, end))
        .thenReturn(List.of(
            snapshot(start, true),
            snapshot(start.plusSeconds(15), true),
            snapshot(start.plusSeconds(30), true),
            snapshot(start.plusSeconds(45), true)));

    CompressorDutyCycle cycle =
        service.getDutyCycles(start, end, Duration.ofMinutes(1)).getFirst();

    assertThat(cycle.load()).isEqualTo(1.0);
    assertThat(cycle.activeCount()).isEqualTo(4);
    assertThat(cycle.count()).isEqualTo(4);
  }

  @Test
  void shouldCalculateHalfDutyCycle() {
    Instant start = Instant.parse("2026-01-01T00:00:00Z");
    Instant end = start.plus(Duration.ofMinutes(1));

    when(repository.findByTimestampBetweenOrderByTimestamp(start, end))
        .thenReturn(List.of(
            snapshot(start, true),
            snapshot(start.plusSeconds(15), true),
            snapshot(start.plusSeconds(30), false),
            snapshot(start.plusSeconds(45), false)));

    CompressorDutyCycle cycle =
        service.getDutyCycles(start, end, Duration.ofMinutes(1)).getFirst();

    assertThat(cycle.load()).isEqualTo(0.5);
    assertThat(cycle.activeCount()).isEqualTo(2);
    assertThat(cycle.count()).isEqualTo(4);
  }

  @Test
  void shouldSplitIntoMultipleBuckets() {
    Instant start = Instant.parse("2026-01-01T00:00:00Z");
    Instant end = start.plus(Duration.ofMinutes(2));

    when(repository.findByTimestampBetweenOrderByTimestamp(start, end))
        .thenReturn(List.of(
            // First minute: 50%
            snapshot(start, true),
            snapshot(start.plusSeconds(15), true),
            snapshot(start.plusSeconds(30), false),
            snapshot(start.plusSeconds(45), false),

            // Second minute: 100%
            snapshot(start.plusSeconds(60), true),
            snapshot(start.plusSeconds(75), true),
            snapshot(start.plusSeconds(90), true),
            snapshot(start.plusSeconds(105), true)));

    List<CompressorDutyCycle> cycles =
        service.getDutyCycles(start, end, Duration.ofMinutes(1));

    assertThat(cycles).hasSize(2);

    assertThat(cycles.getFirst().load()).isEqualTo(0.5);
    assertThat(cycles.getFirst().activeCount()).isEqualTo(2);
    assertThat(cycles.getFirst().count()).isEqualTo(4);

    assertThat(cycles.get(1).load()).isEqualTo(1.0);
    assertThat(cycles.get(1).activeCount()).isEqualTo(4);
    assertThat(cycles.get(1).count()).isEqualTo(4);
  }

  @Test
  void shouldIncludeEmptyBuckets() {
    Instant start = Instant.parse("2026-01-01T00:00:00Z");
    Instant end = start.plus(Duration.ofMinutes(3));

    when(repository.findByTimestampBetweenOrderByTimestamp(start, end))
        .thenReturn(List.of(
            snapshot(start, true),
            snapshot(start.plusSeconds(15), true),

            snapshot(start.plusSeconds(120), false),
            snapshot(start.plusSeconds(135), false)));

    List<CompressorDutyCycle> cycles =
        service.getDutyCycles(start, end, Duration.ofMinutes(1));

    assertThat(cycles).hasSize(3);

    assertThat(cycles.getFirst().startTime()).isEqualTo(start);
    assertThat(cycles.getFirst().activeCount()).isEqualTo(2);
    assertThat(cycles.getFirst().count()).isEqualTo(2);

    assertThat(cycles.get(1).startTime()).isEqualTo(start.plus(Duration.ofMinutes(1)));
    assertThat(cycles.get(1).activeCount()).isZero();
    assertThat(cycles.get(1).count()).isZero();
    assertThat(cycles.get(1).load()).isZero();

    assertThat(cycles.get(2).startTime()).isEqualTo(start.plus(Duration.ofMinutes(2)));
    assertThat(cycles.get(2).activeCount()).isZero();
    assertThat(cycles.get(2).count()).isEqualTo(2);
    assertThat(cycles.get(2).load()).isZero();
  }

  @Test
  void shouldAssignBoundarySnapshotToNextBucket() {
    Instant start = Instant.parse("2026-01-01T00:00:00Z");
    Instant end = start.plus(Duration.ofMinutes(2));

    when(repository.findByTimestampBetweenOrderByTimestamp(start, end))
        .thenReturn(List.of(
            snapshot(start.plusSeconds(60), true)));

    List<CompressorDutyCycle> cycles =
        service.getDutyCycles(start, end, Duration.ofMinutes(1));

    assertThat(cycles).hasSize(2);

    assertThat(cycles.getFirst().startTime()).isEqualTo(start);
    assertThat(cycles.getFirst().count()).isZero();
    assertThat(cycles.getFirst().activeCount()).isZero();
    assertThat(cycles.getFirst().load()).isZero();

    assertThat(cycles.get(1).startTime()).isEqualTo(start.plus(Duration.ofMinutes(1)));
    assertThat(cycles.get(1).count()).isEqualTo(1);
    assertThat(cycles.get(1).activeCount()).isEqualTo(1);
    assertThat(cycles.get(1).load()).isEqualTo(1.0);
  }

  @Test
  void shouldCalculateHalfLoadOverOneHourWith15SecondPolling() {
    Instant start = Instant.parse("2026-01-01T00:00:00Z");
    Instant end = start.plus(Duration.ofHours(1));

    List<HeatPumpSnapshot> snapshots = new java.util.ArrayList<>();

    for (int i = 0; i < 240; ++i) {
      snapshots.add(snapshot(
          start.plusSeconds(i * 15L),
          i < 120));
    }

    when(repository.findByTimestampBetweenOrderByTimestamp(start, end))
        .thenReturn(snapshots);

    CompressorDutyCycle cycle =
        service.getDutyCycles(start, end, Duration.ofHours(1)).getFirst();

    assertThat(cycle.load()).isEqualTo(0.5);
    assertThat(cycle.activeCount()).isEqualTo(120);
    assertThat(cycle.count()).isEqualTo(240);
  }

  private static HeatPumpSnapshot snapshot(final Instant timestamp, final boolean compressorActive) {
    return new HeatPumpSnapshot(timestamp,
        new ControlSnapshot(0, compressorActive),
        null,
        null,
        null);
  }
}
