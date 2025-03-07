/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client;

import com.github.aleksikangas.backend.domain.snapshot.TemperatureSnapshot;
import com.github.aleksikangas.backend.domain.timer.TimerSchedule;
import com.github.aleksikangas.backend.domain.timer.TimerType;

/**
 * Client for accessing information and performing operations on the heat-pump.
 */
public interface HeatPumpClient {

  TemperatureSnapshot readTemperatureSnapshot() throws HeatPumpClientException;

  TimerSchedule readTimerSchedule(TimerType timerType) throws HeatPumpClientException;

  void writeTimerSchedule(TimerType timerType, TimerSchedule timerSchedule) throws HeatPumpClientException;
}
