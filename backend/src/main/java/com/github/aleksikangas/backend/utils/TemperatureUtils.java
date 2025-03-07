/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.utils;

public final class TemperatureUtils {

  private TemperatureUtils() {
  }

  public static float roundToOneDecimalPlace(final float value) {
    final int scale = (int) Math.pow(10, 1);
    return (float) Math.round(value * scale) / scale;
  }
}
