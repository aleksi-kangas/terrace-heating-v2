'use client'
import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {ChartTooltip, LineChart} from "@mantine/charts";
import {DateTime} from "luxon";

interface OutdoorTemperatureChartProps {
  heatPumpSnapshots: HeatPumpSnapshot[];
}

const OutdoorTemperatureChart = ({heatPumpSnapshots}: OutdoorTemperatureChartProps) => {
  const series = [
    {
      name: "temperatureSnapshot.outdoorC",
      label: "Outdoor",
    }
  ];
  const tickLabelFormatter = (timestampIso: string) => DateTime
      .fromISO(timestampIso)
      .toLocaleString({
        weekday: 'short',
        day: 'numeric',
        hour: 'numeric',
        minute: '2-digit',
        hourCycle: "h24",
      });
  const tooltipLabelFormatter = (timestampIso: string) => DateTime
      .fromISO(timestampIso)
      .toLocaleString({
        weekday: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        hourCycle: "h24",
      });
  return (
      <LineChart
          data={heatPumpSnapshots}
          dataKey="timestamp"
          h={400}
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
            height: 50,
            interval: "equidistantPreserveStart",
            tickFormatter: tickLabelFormatter,
          }}
      />
  )
}

export default OutdoorTemperatureChart;
