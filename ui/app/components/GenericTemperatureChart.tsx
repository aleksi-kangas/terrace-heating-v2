'use client';

import {useMemo} from "react";
import {DateTime} from "luxon";
import {LineChart, ChartTooltip} from "@mantine/charts";
import {TemperatureSnapshot} from "@/app/types/snapshot";

interface GenericTemperatureChartProps {
  timestamps: DateTime[];
  temperatureSnapshots: TemperatureSnapshot[];
  xAxisDomainTrailingDays: number;
  series: {
    name: string;
    label: string;
    color: string;
  }[];
}

const GenericTemperatureChart = ({
                                   timestamps,
                                   temperatureSnapshots,
                                   xAxisDomainTrailingDays,
                                   series
                                 }: GenericTemperatureChartProps) => {
  const data = useMemo(() =>
          timestamps.map((timestamp, index) => ({
            timestamp: timestamp.toSeconds(),
            ...temperatureSnapshots[index],
          })),
      [timestamps, temperatureSnapshots]
  );
  const xAxisDomain = useMemo(() => {
    const now: DateTime = DateTime.now();
    return [now.minus({days: xAxisDomainTrailingDays}).toSeconds(), now.toSeconds()]
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
