'use client';

import {useMemo} from "react";
import {DateTime} from "luxon";
import {ChartTooltip, LineChart} from "@mantine/charts";
import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {useMediaQuery} from "@mantine/hooks";
import {tickLabelFormatter, tooltipLabelFormatter} from "@/app/utils/chart-formatters";

interface SeriesItem {
  name: string;
  label: string;
  color: string;
}

interface XAxisProps {
  domainTrailingDays: number;
}

interface GenericTemperatureChartProps {
  heatPumpSnapshots: HeatPumpSnapshot[]
  series: SeriesItem[];
  xAxisProps: XAxisProps;
}

const GenericTemperatureChart = ({
                                   heatPumpSnapshots,
                                   series,
                                   xAxisProps
                                 }: GenericTemperatureChartProps) => {
  const isMobile = useMediaQuery("(max-width: 48em)");

  const data = useMemo(() =>
          heatPumpSnapshots.map((heatPumpSnapshot, index) => ({
            timestamp: DateTime.fromISO(heatPumpSnapshot.timestamp).toSeconds(),
            ...heatPumpSnapshot.temperatureSnapshot
          })),
      [heatPumpSnapshots]
  );

  const tooltipContent = useMemo(
      () => createTemperatureTooltip(series),
      [series]
  );

  const xAxisDomain = useMemo(() => {
    const now: DateTime = DateTime.now();
    return [now.minus({days: xAxisProps.domainTrailingDays}).toSeconds(), now.toSeconds()]
  }, [xAxisProps.domainTrailingDays]);

  const xAxisTickInterval = useMemo(() => {
    const days = xAxisProps.domainTrailingDays;
    if (days <= 1) return isMobile ? 3 : 0;
    if (days <= 2) return isMobile ? 5 : 1;
    if (days <= 3) return isMobile ? 7 : 2;
    return isMobile ? 11 : 3;
  }, [isMobile, xAxisProps.domainTrailingDays])

  const xAxisTicks = useMemo(() => {
    const [start, end] = xAxisDomain;
    const intervalHours = 1;
    const intervalSeconds = intervalHours * 3600;
    const ticks: number[] = [];
    let t = Math.ceil(start / intervalSeconds) * intervalSeconds;
    while (t <= end) {
      ticks.push(t);
      t += intervalSeconds;
    }
    return ticks;
  }, [xAxisDomain]);

  return (
      <LineChart
          data={data}
          dataKey="timestamp"
          h="100%"
          series={series}
          tickLine="xy"
          tooltipProps={{content: tooltipContent}}
          unit="°C"
          withDots={false}
          withLegend={true}
          withTooltip={true}
          xAxisProps={{
            angle: -60,
            axisLine: false,
            domain: xAxisDomain,
            height: 60,
            interval: xAxisTickInterval,
            tickFormatter: (v) => tickLabelFormatter(v, isMobile),
            tickMargin: 30,
            ticks: xAxisTicks,
            type: "number"
          }}
      />
  )
}

export default GenericTemperatureChart;

const createTemperatureTooltip = (series: SeriesItem[]) => {
  const TemperatureTooltip = ({label, payload}: {
    label?: string | number;
    payload?: readonly Record<string, any>[];
  }) => (
      <ChartTooltip
          label={tooltipLabelFormatter(label as number | string | undefined)}
          payload={payload}
          series={series}
      />
  );
  TemperatureTooltip.displayName = 'TemperatureTooltip';
  return TemperatureTooltip;
};
