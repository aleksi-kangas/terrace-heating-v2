'use client'
import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {ChartTooltip, LineChart} from "@mantine/charts";
import {DateTime} from "luxon";
import {useMemo} from "react";

interface TemperatureChartProps {
  heatPumpSnapshots: HeatPumpSnapshot[];
  xAxisDomainTrailingDays: number;
  series: {
    name: string;
    label: string;
  }
}

const TemperatureChart = ({heatPumpSnapshots, xAxisDomainTrailingDays, series}: TemperatureChartProps) => {
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
          h={400}
          series={[series]}
          tooltipProps={{
            content: ({label, payload}) =>
                <ChartTooltip
                    label={tooltipLabelFormatter(label)}
                    payload={payload}
                    series={[series]}
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

export default TemperatureChart;

const tickLabelFormatter = (epochSeconds: number) => DateTime
    .fromSeconds(epochSeconds)
    .toLocaleString({
      weekday: 'short',
      day: 'numeric',
      hour: 'numeric',
      minute: '2-digit',
      hourCycle: "h24",
    });
const tooltipLabelFormatter = (epochSeconds?: number) => {
  if (epochSeconds === undefined)
    return "";
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
