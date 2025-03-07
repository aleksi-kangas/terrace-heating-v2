/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client.utils;

import com.github.aleksikangas.backend.heatpump.client.registers.RegisterRange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record ContiguousRegisterValueRange(RegisterRange registerRange,
                                           short[] values) {

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ContiguousRegisterValueRange that = (ContiguousRegisterValueRange) o;
    return Objects.equals(registerRange, that.registerRange) && Objects.deepEquals(values, that.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(registerRange, Arrays.hashCode(values));
  }

  public static final class Builder {

    private final List<Short> values = new ArrayList<>();
    private RegisterRange registerRange;

    public Builder(final int startRegister, final short startRegisterValue) {
      this.registerRange = new RegisterRange(startRegister, 1);
      this.values.add(startRegisterValue);
    }

    public void extend(short value) {
      registerRange = new RegisterRange(registerRange.startRegister(), registerRange.registerCount() + 1);
      values.add(value);
    }

    public int endRegister() {
      return registerRange.endRegister();
    }

    public ContiguousRegisterValueRange build() {
      final int n = values.size();
      final short[] valueArray = new short[n];
      for (int i = 0; i < n; ++i) {
        valueArray[i] = values.get(i);
      }
      return new ContiguousRegisterValueRange(registerRange, valueArray);
    }
  }
}
