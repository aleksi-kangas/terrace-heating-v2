/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client.utils;

import com.github.aleksikangas.backend.domain.timer.TimerSchedule;
import com.github.aleksikangas.backend.domain.timer.TimerType;
import com.github.aleksikangas.backend.heatpump.client.registers.TimerRegisters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

public final class RegisterUtils {

  private RegisterUtils() {
  }

  public static List<ContiguousRegisterValueRange> extractContiguousRegisterValueRanges(
      final SortedMap<Integer, Short> registerValueMap) {
    final List<ContiguousRegisterValueRange> contiguousRegisterValueRanges = new ArrayList<>();
    Optional<ContiguousRegisterValueRange.Builder> currentBuilder = Optional.empty();
    for (final Map.Entry<Integer, Short> e : registerValueMap.entrySet()) {
      if (currentBuilder.isPresent()) {
        final ContiguousRegisterValueRange.Builder builder = currentBuilder.get();
        if (builder.endRegister() + 1 == e.getKey()) {
          builder.extend(e.getValue());
        } else {
          contiguousRegisterValueRanges.add(builder.build());
          currentBuilder = Optional.of(new ContiguousRegisterValueRange.Builder(e.getKey(), e.getValue()));
        }
      } else {
        currentBuilder = Optional.of(new ContiguousRegisterValueRange.Builder(e.getKey(), e.getValue()));
      }
    }
    currentBuilder.ifPresent(builder -> contiguousRegisterValueRanges.add(builder.build()));
    return contiguousRegisterValueRanges;
  }

  public static SortedMap<Integer, Short> buildRegisterValueMap(final TimerType timerType,
      final TimerSchedule timerSchedule) {
    final TimerRegisters timerRegisters = TimerRegisters.of(timerType);
    final SortedMap<Integer, Short> mapping = new TreeMap<>();

    mapping.put(timerRegisters.mondayRegisters().startHourRegister(),
        timerSchedule.mondaySchedule().startHour());
    mapping.put(timerRegisters.mondayRegisters().endHourRegister(),
        timerSchedule.mondaySchedule().endHour());
    mapping.put(timerRegisters.mondayRegisters().temperatureDeltaCRegister(),
        timerSchedule.mondaySchedule().temperatureDeltaC());

    mapping.put(timerRegisters.tuesdayRegisters().startHourRegister(),
        timerSchedule.tuesdaySchedule().startHour());
    mapping.put(timerRegisters.tuesdayRegisters().endHourRegister(),
        timerSchedule.tuesdaySchedule().endHour());
    mapping.put(timerRegisters.tuesdayRegisters().temperatureDeltaCRegister(),
        timerSchedule.tuesdaySchedule().temperatureDeltaC());

    mapping.put(timerRegisters.wednesdayRegisters().startHourRegister(),
        timerSchedule.wednesdaySchedule().startHour());
    mapping.put(timerRegisters.wednesdayRegisters().endHourRegister(),
        timerSchedule.wednesdaySchedule().endHour());
    mapping.put(timerRegisters.wednesdayRegisters().temperatureDeltaCRegister(),
        timerSchedule.wednesdaySchedule().temperatureDeltaC());

    mapping.put(timerRegisters.thursdayRegisters().startHourRegister(),
        timerSchedule.thursdaySchedule().startHour());
    mapping.put(timerRegisters.thursdayRegisters().endHourRegister(),
        timerSchedule.thursdaySchedule().endHour());
    mapping.put(timerRegisters.thursdayRegisters().temperatureDeltaCRegister(),
        timerSchedule.thursdaySchedule().temperatureDeltaC());

    mapping.put(timerRegisters.fridayRegisters().startHourRegister(),
        timerSchedule.fridaySchedule().startHour());
    mapping.put(timerRegisters.fridayRegisters().endHourRegister(),
        timerSchedule.fridaySchedule().endHour());
    mapping.put(timerRegisters.fridayRegisters().temperatureDeltaCRegister(),
        timerSchedule.fridaySchedule().temperatureDeltaC());

    mapping.put(timerRegisters.saturdayRegisters().startHourRegister(),
        timerSchedule.saturdaySchedule().startHour());
    mapping.put(timerRegisters.saturdayRegisters().endHourRegister(),
        timerSchedule.saturdaySchedule().endHour());
    mapping.put(timerRegisters.saturdayRegisters().temperatureDeltaCRegister(),
        timerSchedule.saturdaySchedule().temperatureDeltaC());

    mapping.put(timerRegisters.sundayRegisters().startHourRegister(),
        timerSchedule.sundaySchedule().startHour());
    mapping.put(timerRegisters.sundayRegisters().endHourRegister(),
        timerSchedule.sundaySchedule().endHour());
    mapping.put(timerRegisters.sundayRegisters().temperatureDeltaCRegister(),
        timerSchedule.sundaySchedule().temperatureDeltaC());

    return mapping;
  }
}
