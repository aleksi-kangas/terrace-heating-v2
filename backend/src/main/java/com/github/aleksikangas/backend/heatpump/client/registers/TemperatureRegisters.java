/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client.registers;

public class TemperatureRegisters {

  public static int outdoor = 1;
  public static int hotGas1 = 2;
  public static int hotGas2 = 3;
  public static int heatDistributionCircuit1 = 5;
  public static int heatDistributionCircuit2 = 6;
  public static int lowerStorageTank = 17;
  public static int upperStorageTank = 18;
  public static int indoor = 74;
  public static int groundCircuitOut = 98;
  public static int groundCircuitIn = 99;
  public static int heatDistributionCircuit3 = 117;

  public static RegisterRange getRegisterRange() {
    return new RegisterRange(1, 117);
  }
}
