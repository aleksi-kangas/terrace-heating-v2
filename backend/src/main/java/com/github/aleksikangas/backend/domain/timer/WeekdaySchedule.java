/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.domain.timer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * A timer weekday schedule.
 *
 * @param startHour         start hour for applying the delta
 * @param endHour           end hour for applying the delta
 * @param temperatureDeltaC temperature delta to apply, in Celsius
 */
public record WeekdaySchedule(@Min(value = 0, message = "startHour must be >= 0")
                              @Max(value = 24, message = "startHour must be <= 24") short startHour,
                              @Min(value = 0, message = "endHour must be >= 0")
                              @Max(value = 24, message = "endHour must be <= 24")
                              short endHour,
                              @Min(value = -10, message = "temperatureDeltaC must be >= -10")
                              @Max(value = 10, message = "temperatureDeltaC must be <= 10")
                              short temperatureDeltaC) {

}
