import GenericTemperatureChart from "../../components/GenericTemperatureChart";

export const chartRegistry = {
  external: {
    component: GenericTemperatureChart,
    series: [
      {
        name: "outdoorC",
        label: "Outdoor °C",
        color: "blue"
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
  },

  tanks: {
    component: GenericTemperatureChart,
    series: [
      {
        name: "upperStorageTankC",
        label: "Top Tank °C",
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
        color: "red"
      },
      {
        name: "indoorC",
        label: "Indoor °C",
        color: "green"
      },
    ],
  },
};

export type ChartSelection = keyof typeof chartRegistry;
