export interface HeatPumpSnapshot {
  timestamp: string;
  temperatureSnapshot: TemperatureSnapshot;
  lowerStorageTankLimitSnapshot: StorageTankLimitSnapshot
  upperStorageTankLimitSnapshot: StorageTankLimitSnapshot
}

export interface TemperatureSnapshot {
  groundCircuitInC: number;
  groundCircuitOutC: number;

  heatDistributionCircuit1C: number;
  heatDistributionCircuit2C: number;
  heatDistributionCircuit3C: number;

  hotGas1C: number;
  hotGas2C: number;

  indoorC: number;
  outdoorC: number;

  lowerStorageTankC: number;
  upperStorageTankC: number;
}

export interface StorageTankLimitSnapshot {
  storageTankMinimumC: number;
  storageTankMaximumC: number;
  storageTankMinimumAdjustedC: number;
  storageTankMaximumAdjustedC: number;
}
