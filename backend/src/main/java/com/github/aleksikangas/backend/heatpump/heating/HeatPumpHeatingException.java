package com.github.aleksikangas.backend.heatpump.heating;

public final class HeatPumpHeatingException extends RuntimeException {

  public HeatPumpHeatingException(final Throwable cause) {
    super(cause);
  }

  public HeatPumpHeatingException(final String message) {
    super(message);
  }
}
