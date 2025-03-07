/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.controller.heatpump.timer;

import com.github.aleksikangas.backend.domain.timer.TimerSchedule;
import com.github.aleksikangas.backend.domain.timer.TimerType;
import jakarta.validation.Valid;

public record UpdateTimerScheduleRequest(TimerType timerType,
                                         @Valid TimerSchedule timerSchedule) {

}
