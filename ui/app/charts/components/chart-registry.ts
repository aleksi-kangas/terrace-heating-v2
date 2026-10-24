import GenericTemperatureChart, {SeriesItem, XAxisProps, YAxisProps} from "../../components/GenericTemperatureChart";
import {ComponentType} from "react";
import {HeatPumpSnapshot} from "@/app/types/snapshot";

export interface ChartRegistryConfig {
  component: ComponentType<{
    heatPumpSnapshots: HeatPumpSnapshot[];
    series: SeriesItem[];
    xAxisProps: XAxisProps;
    yAxisProps?: YAxisProps;
    rightYAxisProps?: YAxisProps;
  }>;
  series: SeriesItem[];
  yAxisProps?: YAxisProps;
  rightYAxisProps?: YAxisProps;
}

export type ChartRegistryKey = "external" | "tanks" | "circuits"

export interface ChartRegistry {
  external: ChartRegistryConfig;
  tanks: ChartRegistryConfig;
  circuits: ChartRegistryConfig;
}

export const chartRegistry: ChartRegistry = {
  external: {
    component: GenericTemperatureChart,
    series: [
      {
        name: "outdoorC",
        label: "Outdoor °C",
        color: "blue",
        yAxisId: "right"
      },
      {
        name: "groundCircuitInC",
        label: "Ground Circuit - Input °C",
        color: "orange"
      },
      {
        name: "groundCircuitOutC",
        label: "Ground Circuit - Output °C",
        color: "red"
      },
    ],
    yAxisProps: {
      domain: ["auto", "auto"],
      label: "Ground Circuits",
      tickCount: 10,
    },
    rightYAxisProps: {
      domain: ["auto", "auto"],
      label: "Outdoor",
      tickCount: 10,
    }
  },

  tanks: {
    component: GenericTemperatureChart,
    series: [
      {
        name: "upperStorageTankC",
        label: "Upper Tank °C",
        color: "orange"
      },
      {
        name: "lowerStorageTankC",
        label: "Lower Tank °C",
        color: "blue"
      },
      {
        name: "hotGas1C",
        label: "Hot Gas 1 °C",
        color: "red"
      },
    ],
    yAxisProps: {
      allowDataOverflow: true,
      domain: [20, 90],
      tickCount: 10,
    }
  },

  circuits: {
    component: GenericTemperatureChart,
    series: [
      {
        name: "heatDistributionCircuit1C",
        label: "Circuit 1 °C",
        color: "blue"
      },
      {
        name: "heatDistributionCircuit2C",
        label: "Circuit 2 °C",
        color: "orange"
      },
      {
        name: "heatDistributionCircuit3C",
        label: "Circuit 3 °C",
        color: "red",
        yAxisId: "right"
      },
      {
        name: "indoorC",
        label: "Indoor °C",
        color: "green"
      },
    ],
    yAxisProps: {
      domain: ["auto", "auto"],
      label: "Circuit 1, Circuit 2 & Indoor",
      tickCount: 10,
    },
    rightYAxisProps: {
      domain: ["auto", "auto"],
      label: "Circuit 3",
      tickCount: 10,
    }
  },
};
