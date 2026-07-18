import GenericTemperatureChart from "../../components/GenericTemperatureChart";

export const chartRegistry = {
  external: {
    component: GenericTemperatureChart,
    series: [
      {
        name: "temperatureSnapshot.outdoorC",
        label: "Outdoor °C",
        color: "blue"
      },
      {
        name: "temperatureSnapshot.groundCircuitInC",
        label: "Ground Circuit - Input °C",
        color: "orange"
      },
      {
        name: "temperatureSnapshot.groundCircuitOutC",
        label: "Ground Circuit - Output °C",
        color: "red"
      },
    ],
  },

  tanks: {
    component: GenericTemperatureChart,
    series: [
      {
        name: "temperatureSnapshot.upperStorageTankC",
        label: "Top Tank °C",
        color: "orange"
      },
      {
        name: "temperatureSnapshot.lowerStorageTankC",
        label: "Lower Tank °C",
        color: "blue"
      },
      {
        name: "temperatureSnapshot.hotGas1C",
        label: "Hot Gas 1 °C",
        color: "red"
      },
    ],
  },

  heatDistributionCircuits: {
    component: GenericTemperatureChart,
    series: [
      {
        name: "temperatureSnapshot.heatDistributionCircuit1C",
        label: "Circuit 1 °C",
        color: "blue"
      },
      {
        name: "temperatureSnapshot.heatDistributionCircuit2C",
        label: "Circuit 2 °C",
        color: "orange"
      },
      {
        name: "temperatureSnapshot.heatDistributionCircuit3C",
        label: "Circuit 3 °C",
        color: "red"
      },
      {
        name: "temperatureSnapshot.indoorC",
        label: "Indoor °C",
        color: "green"
      },
    ],
  },
};

export type ChartSelection = keyof typeof chartRegistry;
