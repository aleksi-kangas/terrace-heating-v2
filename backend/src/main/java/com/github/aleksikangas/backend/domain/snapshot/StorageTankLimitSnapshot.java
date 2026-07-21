/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.domain.snapshot;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import org.springframework.data.annotation.Immutable;

/**
 * A snapshot of heat-pump storage tank limits, which guide the heat-pump run operations.
 *
 * @see HeatPumpSnapshot
 */
@Embeddable
@Immutable
public record StorageTankLimitSnapshot(float storageTankMinimumC,
                                       float storageTankMaximumC,
                                       float storageTankMinimumAdjustedC,
                                       float storageTankMaximumAdjustedC) implements Serializable {

}
