package com.github.aleksikangas.backend.domain.snapshot;

import com.github.aleksikangas.backend.persistence.core.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

/**
 * A snapshot of heat-pump compressor.
 *
 * @see HeatPumpSnapshot
 */
@AllArgsConstructor
@Entity
@Getter
@Immutable
@NoArgsConstructor
@Table(name = "compressor_snapshots")
public final class CompressorSnapshot extends AbstractEntity {

  @Column(updatable = false)
  private boolean isActive;
}
