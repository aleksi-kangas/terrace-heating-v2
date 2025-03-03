/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client;

import com.github.aleksikangas.backend.domain.snapshot.TemperatureSnapshot;

/**
 * Client for accessing information and performing operations on the heat-pump.
 */
public interface HeatPumpClient {

  TemperatureSnapshot readTemperatureSnapshot() throws HeatPumpClientException;
}
