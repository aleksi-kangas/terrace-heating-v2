'use client';

import {useEffect, useState} from "react";

import ChartSelection from "./ChartSelection";
import DaysSelection from "./DaysSelection";

import {
  chartRegistry,
  ChartSelection as ChartSelectionType,
} from "./chart-registry";

import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {fetchHeatPumpSnapshotsTrailingDays} from "@/app/api/heat-pump/snapshots";
import {Card, Stack} from "@mantine/core";

const availableCharts = [
  {label: "External", value: "external"},
  {label: "Tanks", value: "tanks"},
  {
    label: "Heat Distribution Circuits",
    value: "heatDistributionCircuits",
  },
];

const availableDays = [
  {label: "7 Days", value: "7"},
  {label: "2 Days", value: "2"},
  {label: "1 Day", value: "1"},
];

interface Props {
  initialHeatPumpSnapshots: HeatPumpSnapshot[];
}

const ChartsPanel = ({initialHeatPumpSnapshots}: Props) => {
  const [heatPumpSnapshots, setHeatPumpSnapshots] =
      useState(initialHeatPumpSnapshots);

  const [chartSelection, setChartSelection] =
      useState<ChartSelectionType>("external");

  const [daysSelection, setDaysSelection] =
      useState("1");

  useEffect(() => {
    if (daysSelection === "1") {
      setHeatPumpSnapshots(initialHeatPumpSnapshots);
      return;
    }

    const load = async () => {
      setHeatPumpSnapshots(
          await fetchHeatPumpSnapshotsTrailingDays(Number(daysSelection))
      );
    };

    load();
  }, [daysSelection, initialHeatPumpSnapshots]);

  const selectedChart = chartRegistry[chartSelection];

  const SelectedChart = selectedChart.component;

  return (
      <Stack h="100%">
        <ChartSelection
            selection={chartSelection}
            onSelectionChange={(value) => setChartSelection(value as ChartSelectionType)}
            values={availableCharts}
        />
        <Card radius="md" shadow="sm" withBorder style={{ flex: 1, minHeight: 0 }}>
          <SelectedChart
              heatPumpSnapshots={heatPumpSnapshots}
              xAxisDomainTrailingDays={Number(daysSelection)}
              series={selectedChart.series}
          />
        </Card>
        <DaysSelection
            selection={daysSelection}
            onSelectionChange={setDaysSelection}
            values={availableDays}
        />
      </Stack>
  );
};

export default ChartsPanel;
