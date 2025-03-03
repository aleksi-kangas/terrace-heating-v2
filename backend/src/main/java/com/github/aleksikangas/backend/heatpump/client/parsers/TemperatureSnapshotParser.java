/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client.parsers;

import com.github.aleksikangas.backend.domain.snapshot.TemperatureSnapshot;
import com.github.aleksikangas.backend.heatpump.client.registers.RegisterRange;
import com.github.aleksikangas.backend.heatpump.client.registers.TemperatureRegisters;

public class TemperatureSnapshotParser {

  private static final float GAIN = 0.1f;

  public static TemperatureSnapshot parse(final short[] temperatureRegisterValues) {
    final RegisterRange temperatureRegisterRange = TemperatureRegisters.getRegisterRange();
    return new TemperatureSnapshot(
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.groundCircuitIn, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.groundCircuitOut, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.heatDistributionCircuit1, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.heatDistributionCircuit2, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.heatDistributionCircuit3, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.hotGas1, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.hotGas2, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.indoor, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.lowerStorageTank, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.outdoor, temperatureRegisterValues),
        getRegisterValue(
            temperatureRegisterRange, TemperatureRegisters.upperStorageTank, temperatureRegisterValues));
  }

  private static float getRegisterValue(
      final RegisterRange registerRange,
      final int register,
      final short[] registerValues) {
    final int registerOffset = register - registerRange.startRegister();
    return registerValues[registerOffset] * GAIN;
  }

  private TemperatureSnapshotParser() {

  }
}
