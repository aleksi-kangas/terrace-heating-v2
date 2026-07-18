'use client';

import {useMemo} from "react";
import {DateTime} from "luxon";
import {LineChart, ChartTooltip} from "@mantine/charts";
import {HeatPumpSnapshot} from "@/app/types/snapshot";

interface GenericTemperatureChartProps {
  heatPumpSnapshots: HeatPumpSnapshot[];
  xAxisDomainTrailingDays: number;
  series: {
    name: string;
    label: string;
    color: string;
  }[];
}

const GenericTemperatureChart = ({heatPumpSnapshots, xAxisDomainTrailingDays, series}: GenericTemperatureChartProps) => {
  const data = useMemo(() => heatPumpSnapshots.map(heatPumpSnapshot => ({
    ...heatPumpSnapshot,
    timestamp: DateTime.fromISO(heatPumpSnapshot.timestamp).toSeconds(),
  })), [heatPumpSnapshots]);
  const xAxisDomain = useMemo(() => {
    const now: DateTime = DateTime.now();
    return [now.minus({day: xAxisDomainTrailingDays}).toSeconds(), now.toSeconds()]
  }, [xAxisDomainTrailingDays]);
  return (
      <LineChart
          data={data}
          dataKey="timestamp"
          h="100%"
          series={series}
          tooltipProps={{
            content: ({label, payload}) =>
                <ChartTooltip
                    label={tooltipLabelFormatter(label)}
                    payload={payload}
                    series={series}
                />
          }}
          unit="°C"
          withDots={false}
          withLegend={true}
          withTooltip={true}
          xAxisProps={{
            angle: -60,
            axisLine: false,
            domain: xAxisDomain,
            height: 50,
            tickFormatter: tickLabelFormatter,
            type: "number"
          }}
      />
  )
}

export default GenericTemperatureChart;

const tickLabelFormatter = (epochSeconds: number) => DateTime
    .fromSeconds(epochSeconds)
    .toLocaleString({
      weekday: 'short',
      day: 'numeric',
      hour: 'numeric',
      minute: '2-digit',
      hourCycle: "h24",
    });
const tooltipLabelFormatter = (value?: number | string) => {
  if (value == null) return "";
  const epochSeconds = typeof value === "string" ? Number(value) : value;
  if (Number.isNaN(epochSeconds)) return "";
  return DateTime
      .fromSeconds(epochSeconds)
      .toLocaleString({
        weekday: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        hourCycle: "h24",
      });
}
