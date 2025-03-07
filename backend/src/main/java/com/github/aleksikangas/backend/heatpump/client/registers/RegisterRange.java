/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client.registers;

import java.util.Arrays;

public record RegisterRange(int startRegister, int registerCount) {

  public static RegisterRange of(final int[] registers) {
    final int minimumRegister = Arrays.stream(registers).min().orElseThrow();
    final int maximumRegister = Arrays.stream(registers).max().orElseThrow();
    final int registerCount = maximumRegister - minimumRegister + 1;
    return new RegisterRange(minimumRegister, registerCount);
  }

  public int endRegister() {
    return startRegister + registerCount - 1;
  }
}
