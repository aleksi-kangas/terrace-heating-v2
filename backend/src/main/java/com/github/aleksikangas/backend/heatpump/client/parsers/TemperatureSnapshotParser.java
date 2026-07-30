/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client.parsers;

import com.github.aleksikangas.backend.domain.snapshot.TemperatureSnapshot;
import com.github.aleksikangas.backend.heatpump.client.registers.RegisterRange;
import com.github.aleksikangas.backend.heatpump.client.registers.TemperatureRegisters;
import com.github.aleksikangas.backend.utils.TemperatureUtils;

public final class TemperatureSnapshotParser {

  private static final float GAIN = 0.1f;

  public static TemperatureSnapshot parse(final short[] temperatureRegisterValues) {
    final RegisterRange temperatureRegisterRange = TemperatureRegisters.getRegisterRange();
    return new TemperatureSnapshot(
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.GROUND_CIRCUIT_IN, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.GROUND_CIRCUIT_OUT, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.HEAT_DISTRIBUTION_CIRCUIT_1, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.HEAT_DISTRIBUTION_CIRCUIT_2, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.HEAT_DISTRIBUTION_CIRCUIT_3, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.HOT_GAS_1, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.HOT_GAS_2, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.INDOOR, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.LOWER_STORAGE_TANK, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.OUTDOOR, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.UPPER_STORAGE_TANK, temperatureRegisterValues));
  }

  private static float getRegisterValue(
      final RegisterRange registerRange,
      final int register,
      final short[] registerValues) {
    final int registerOffset = register - registerRange.startRegister();
    return TemperatureUtils.roundToOneDecimalPlace(registerValues[registerOffset] * GAIN);
  }

  private TemperatureSnapshotParser() {

  }
}
