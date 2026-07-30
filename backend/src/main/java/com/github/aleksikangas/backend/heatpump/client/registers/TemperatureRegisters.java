/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client.registers;

public final class TemperatureRegisters {

  public static final int OUTDOOR = 1;
  public static final int HOT_GAS_1 = 2;
  public static final int HOT_GAS_2 = 3;
  public static final int HEAT_DISTRIBUTION_CIRCUIT_1 = 5;
  public static final int HEAT_DISTRIBUTION_CIRCUIT_2 = 6;
  public static final int LOWER_STORAGE_TANK = 17;
  public static final int UPPER_STORAGE_TANK = 18;
  public static final int INDOOR = 74;
  public static final int GROUND_CIRCUIT_OUT = 98;
  public static final int GROUND_CIRCUIT_IN = 99;
  public static final int HEAT_DISTRIBUTION_CIRCUIT_3 = 117;

  public static RegisterRange getRegisterRange() {
    return new RegisterRange(1, 117);
  }

  private TemperatureRegisters() {
  }
}
