/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client.registers;

import com.github.aleksikangas.backend.domain.timer.TimerType;
import java.util.stream.IntStream;

public record TimerRegisters(WeekdayRegisters mondayRegisters,
                             WeekdayRegisters tuesdayRegisters,
                             WeekdayRegisters wednesdayRegisters,
                             WeekdayRegisters thursdayRegisters,
                             WeekdayRegisters fridayRegisters,
                             WeekdayRegisters saturdayRegisters,
                             WeekdayRegisters sundayRegisters) {

  public static final TimerRegisters HEAT_DISTRIBUTION_CIRCUIT_3 = new TimerRegisters(
      new WeekdayRegisters(5214, 5213, 107),
      new WeekdayRegisters(5211, 5212, 106),
      new WeekdayRegisters(5220, 5221, 110),
      new WeekdayRegisters(5222, 5215, 111),
      new WeekdayRegisters(5223, 5224, 112),
      new WeekdayRegisters(5216, 5217, 108),
      new WeekdayRegisters(5218, 5219, 109));
  public static final TimerRegisters LOW_STORAGE_TANK = new TimerRegisters(
      new WeekdayRegisters(5014, 5021, 36),
      new WeekdayRegisters(5015, 5022, 37),
      new WeekdayRegisters(5016, 5023, 38),
      new WeekdayRegisters(5017, 5024, 39),
      new WeekdayRegisters(5018, 5025, 41),
      new WeekdayRegisters(5019, 5026, 42),
      new WeekdayRegisters(5020, 5027, 43));

  public static TimerRegisters of(final TimerType timerType) {
    return switch (timerType) {
      case HEAT_DISTRIBUTION_CIRCUIT_3 -> HEAT_DISTRIBUTION_CIRCUIT_3;
      case LOWER_STORAGE_TANK -> LOW_STORAGE_TANK;
      default -> throw new UnsupportedOperationException("Not implemented");
    };
  }

  public RegisterRange getStartEndHourRegisterRange() {
    final int[] startEndHourRegisters = IntStream
        .of(
            mondayRegisters.startHourRegister(),
            mondayRegisters.endHourRegister(),
            tuesdayRegisters.startHourRegister(),
            tuesdayRegisters.endHourRegister(),
            wednesdayRegisters.startHourRegister(),
            wednesdayRegisters.endHourRegister(),
            thursdayRegisters.startHourRegister(),
            thursdayRegisters.endHourRegister(),
            fridayRegisters.startHourRegister(),
            fridayRegisters.endHourRegister(),
            saturdayRegisters.startHourRegister(),
            saturdayRegisters.endHourRegister(),
            sundayRegisters.startHourRegister(),
            sundayRegisters.endHourRegister())
        .toArray();
    return RegisterRange.of(startEndHourRegisters);
  }

  public RegisterRange getTemperatureDeltaRegisterRange() {
    final int[] temperatureDeltaRegisters = IntStream
        .of(
            mondayRegisters.temperatureDeltaCRegister(),
            tuesdayRegisters.temperatureDeltaCRegister(),
            wednesdayRegisters.temperatureDeltaCRegister(),
            thursdayRegisters.temperatureDeltaCRegister(),
            fridayRegisters.temperatureDeltaCRegister(),
            saturdayRegisters.temperatureDeltaCRegister(),
            sundayRegisters.temperatureDeltaCRegister())
        .toArray();
    return RegisterRange.of(temperatureDeltaRegisters);
  }

  public record WeekdayRegisters(int startHourRegister,
                                 int endHourRegister,
                                 int temperatureDeltaCRegister) {

  }
}
