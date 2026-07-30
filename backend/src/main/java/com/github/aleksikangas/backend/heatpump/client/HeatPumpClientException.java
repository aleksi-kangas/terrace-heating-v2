/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client;

public final class HeatPumpClientException extends RuntimeException {

  public HeatPumpClientException(final Throwable cause) {
    super(cause);
  }

  public HeatPumpClientException(final String message) {
    super(message);
  }
}
