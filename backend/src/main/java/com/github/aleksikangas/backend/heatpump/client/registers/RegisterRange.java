/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client.registers;

public record RegisterRange(int startRegister, int registerCount) {

  public int endRegister() {
    return startRegister + registerCount - 1;
  }
}
