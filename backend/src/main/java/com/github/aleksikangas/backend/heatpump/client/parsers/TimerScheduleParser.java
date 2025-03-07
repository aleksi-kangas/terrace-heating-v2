/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client.parsers;

import com.github.aleksikangas.backend.domain.timer.TimerSchedule;
import com.github.aleksikangas.backend.domain.timer.TimerType;
import com.github.aleksikangas.backend.domain.timer.WeekdaySchedule;
import com.github.aleksikangas.backend.heatpump.client.registers.RegisterRange;
import com.github.aleksikangas.backend.heatpump.client.registers.TimerRegisters;
import com.github.aleksikangas.backend.heatpump.client.registers.TimerRegisters.WeekdayRegisters;

public final class TimerScheduleParser {

  private TimerScheduleParser() {

  }

  public static TimerSchedule parse(
      final TimerType timerType,
      final short[] startEndHourRegisterValues,
      final short[] temperatureDeltaRegisterValues) {
    final TimerRegisters timerRegisters = TimerRegisters.of(timerType);
    final RegisterRange startEndHourRegisterRange = timerRegisters.getStartEndHourRegisterRange();
    final RegisterRange temperatureDeltaRegisterRange = timerRegisters.getTemperatureDeltaRegisterRange();
    return new TimerSchedule(
        parseWeekdaySchedule(
            timerRegisters.mondayRegisters(),
            startEndHourRegisterRange,
            startEndHourRegisterValues,
            temperatureDeltaRegisterRange,
            temperatureDeltaRegisterValues
        ),
        parseWeekdaySchedule(
            timerRegisters.tuesdayRegisters(),
            startEndHourRegisterRange,
            startEndHourRegisterValues,
            temperatureDeltaRegisterRange,
            temperatureDeltaRegisterValues
        ),
        parseWeekdaySchedule(
            timerRegisters.wednesdayRegisters(),
            startEndHourRegisterRange,
            startEndHourRegisterValues,
            temperatureDeltaRegisterRange,
            temperatureDeltaRegisterValues
        ),
        parseWeekdaySchedule(
            timerRegisters.thursdayRegisters(),
            startEndHourRegisterRange,
            startEndHourRegisterValues,
            temperatureDeltaRegisterRange,
            temperatureDeltaRegisterValues
        ),
        parseWeekdaySchedule(
            timerRegisters.fridayRegisters(),
            startEndHourRegisterRange,
            startEndHourRegisterValues,
            temperatureDeltaRegisterRange,
            temperatureDeltaRegisterValues
        ),
        parseWeekdaySchedule(
            timerRegisters.saturdayRegisters(),
            startEndHourRegisterRange,
            startEndHourRegisterValues,
            temperatureDeltaRegisterRange,
            temperatureDeltaRegisterValues
        ),
        parseWeekdaySchedule(
            timerRegisters.sundayRegisters(),
            startEndHourRegisterRange,
            startEndHourRegisterValues,
            temperatureDeltaRegisterRange,
            temperatureDeltaRegisterValues
        ));
  }

  private static WeekdaySchedule parseWeekdaySchedule(
      final WeekdayRegisters weekdayRegisters,
      final RegisterRange startEndHourRegisterRange,
      final short[] startEndHourRegisterValues,
      final RegisterRange temperatureDeltaRegisterRange,
      final short[] temperatureDeltaRegisterValues) {
    return new WeekdaySchedule(
        getRegisterValue(
            startEndHourRegisterRange,
            weekdayRegisters.startHourRegister(),
            startEndHourRegisterValues),
        getRegisterValue(
            startEndHourRegisterRange,
            weekdayRegisters.endHourRegister(),
            startEndHourRegisterValues),
        getRegisterValue(
            temperatureDeltaRegisterRange,
            weekdayRegisters.temperatureDeltaCRegister(),
            temperatureDeltaRegisterValues));
  }

  private static short getRegisterValue(
      final RegisterRange registerRange,
      final int register,
      final short[] registerValues) {
    final int registerOffset = register - registerRange.startRegister();
    return registerValues[registerOffset];
  }
}
