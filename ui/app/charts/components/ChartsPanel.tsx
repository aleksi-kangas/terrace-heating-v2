'use client';

import {useState, useTransition} from "react";

import ChartSelection from "./ChartSelection";
import DaysSelection from "./DaysSelection";

import {chartRegistry, ChartSelection as ChartSelectionType,} from "./chart-registry";

import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {Box, Card, LoadingOverlay, Stack} from "@mantine/core";
import {usePathname, useRouter} from "next/navigation";

const availableCharts = [
  {label: "External", value: "external"},
  {label: "Tanks", value: "tanks"},
  {label: "Circuits", value: "circuits"},
];

const availableDays = [
  {label: "7 Days", value: "7"},
  {label: "2 Days", value: "2"},
  {label: "1 Day", value: "1"},
];

interface ChartsPanelProps {
  heatPumpSnapshots: HeatPumpSnapshot[];
  trailingDays: number;
}

const ChartsPanel = ({heatPumpSnapshots, trailingDays}: ChartsPanelProps) => {
  const router = useRouter();
  const pathname = usePathname();

  const [chartSelection, setChartSelection] = useState<ChartSelectionType>("external");
  const [isPending, startTransition] = useTransition();

  const selectedChart = chartRegistry[chartSelection];
  const SelectedChart = selectedChart.component;

  return (
      <Stack h="100%">
        <ChartSelection
            selection={chartSelection}
            onSelectionChange={(value) => setChartSelection(value as ChartSelectionType)}
            values={availableCharts}
        />
        <Card radius="md" shadow="sm" withBorder style={{flex: 1, minHeight: 0}}>
          <Box h="100%" p="xs">
            <LoadingOverlay visible={isPending} zIndex={10} overlayProps={{radius: "md", blur: 1}}/>
            <SelectedChart
                heatPumpSnapshots={heatPumpSnapshots}
                series={selectedChart.series}
                xAxisProps={{
                  domainTrailingDays: trailingDays,
                }}
            />
          </Box>
        </Card>
        <DaysSelection
            selection={String(trailingDays)}
            onSelectionChange={(value) => {
              startTransition(() => {
                router.push(`${pathname}?days=${value}`);
              });
            }}
            values={availableDays}
            disabled={isPending}
        />
      </Stack>
  );
};

export default ChartsPanel;
