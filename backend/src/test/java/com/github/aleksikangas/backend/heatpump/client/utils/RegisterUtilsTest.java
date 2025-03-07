/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.aleksikangas.backend.heatpump.client.registers.RegisterRange;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

public final class RegisterUtilsTest {

  private static final SortedMap<Integer, Short> SORTED_REGISTER_VALUE_MAP = new TreeMap<>();

  static {
    SORTED_REGISTER_VALUE_MAP.put(1, (short) 1);
    SORTED_REGISTER_VALUE_MAP.put(2, (short) 2);
    SORTED_REGISTER_VALUE_MAP.put(3, (short) 3);

    SORTED_REGISTER_VALUE_MAP.put(5, (short) 5);
    SORTED_REGISTER_VALUE_MAP.put(6, (short) 6);

    SORTED_REGISTER_VALUE_MAP.put(8, (short) 8);

    SORTED_REGISTER_VALUE_MAP.put(10, (short) 10);
    SORTED_REGISTER_VALUE_MAP.put(11, (short) 11);
    SORTED_REGISTER_VALUE_MAP.put(12, (short) 12);
    SORTED_REGISTER_VALUE_MAP.put(13, (short) 13);

    SORTED_REGISTER_VALUE_MAP.put(15, (short) 15);
    SORTED_REGISTER_VALUE_MAP.put(16, (short) 16);
    SORTED_REGISTER_VALUE_MAP.put(17, (short) 17);

    SORTED_REGISTER_VALUE_MAP.put(20, (short) 20);
  }

  @Test
  public void getContiguousRegisterValueRanges_returnsContiguousRegisterValueRanges() {
    final List<ContiguousRegisterValueRange> result = RegisterUtils.extractContiguousRegisterValueRanges(
        SORTED_REGISTER_VALUE_MAP);
    final List<ContiguousRegisterValueRange> expected = List.of(
        new ContiguousRegisterValueRange(new RegisterRange(1, 3), new short[]{1, 2, 3}),
        new ContiguousRegisterValueRange(new RegisterRange(5, 2), new short[]{5, 6}),
        new ContiguousRegisterValueRange(new RegisterRange(8, 1), new short[]{8}),
        new ContiguousRegisterValueRange(new RegisterRange(10, 4), new short[]{10, 11, 12, 13}),
        new ContiguousRegisterValueRange(new RegisterRange(15, 3), new short[]{15, 16, 17}),
        new ContiguousRegisterValueRange(new RegisterRange(20, 1), new short[]{20}));
    assertEquals(expected.size(), result.size());
    assertEquals(expected, result);
  }
}
