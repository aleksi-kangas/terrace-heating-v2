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

  /**
   * Read the number of active heat distribution circuits.
   *
   * @return number of active heat distribution circuits
   * @throws HeatPumpClientException on failure
   */
  short readActiveHeatDistributionCircuitCount();

  /**
   * Write the number of active heat distribution circuits.
   *
   * @param activeHeatDistributionCircuitCount to write
   * @throws HeatPumpClientException on failure
   */
  void writeActiveHeatDistributionCircuitCount(short activeHeatDistributionCircuitCount);

  /**
   * Read the current temperatures as {@link TemperatureSnapshot} from the heat-pump.
   *
   * @return a {@link TemperatureSnapshot} of the current temperatures
   * @throws HeatPumpClientException on failure
   */
  TemperatureSnapshot readTemperatureSnapshot();

  /**
   * Read the {@link TimerSchedule} for the given {@link TimerType}.
   *
   * @param timerType of interest
   * @return the current {@link TimerSchedule} of the given {@link TimerType}
   * @throws HeatPumpClientException on failure
   */
  TimerSchedule readTimerSchedule(TimerType timerType);

  /**
   * Write the given {@link TimerSchedule} as the current of the given {@link TimerType}.
   *
   * @param timerType     of interest
   * @param timerSchedule to write
   * @throws HeatPumpClientException on failure
   */
  void writeTimerSchedule(TimerType timerType, TimerSchedule timerSchedule);
}
