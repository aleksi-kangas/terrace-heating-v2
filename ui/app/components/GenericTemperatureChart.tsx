'use client';

import {useMemo} from "react";
import {DateTime} from "luxon";
import {ChartTooltip, LineChart} from "@mantine/charts";
import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {useMediaQuery} from "@mantine/hooks";
import {roundingTickLabelFormatter, tickLabelFormatter, tooltipLabelFormatter} from "@/app/utils/chart-formatters";
import {TooltipContentProps} from "recharts";
import {AxisDomain} from "recharts/types/util/types";

export interface SeriesItem {
  name: string;
  label: string;
  color: string;
  yAxisId?: "left" | "right";
}

export interface XAxisProps {
  domainTrailingDays: number;
}

export interface YAxisProps {
  allowDataOverflow?: boolean;
  domain?: AxisDomain;
  label?: string;
  tickCount?: number;
}

interface GenericTemperatureChartProps {
  heatPumpSnapshots: HeatPumpSnapshot[]
  series: SeriesItem[];
  xAxisProps: XAxisProps;
  yAxisProps?: YAxisProps;
  rightYAxisProps?: YAxisProps;
}

const GenericTemperatureChart = ({
                                   heatPumpSnapshots,
                                   series,
                                   xAxisProps,
                                   yAxisProps,
                                   rightYAxisProps
                                 }: GenericTemperatureChartProps) => {
  const isMobile = useMediaQuery("(max-width: 48em)");

  const data = useMemo(() =>
          heatPumpSnapshots.map((heatPumpSnapshot) => ({
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
          gridAxis="xy"
          h="100%"
          series={series}
          tickLine="xy"
          tooltipProps={{content: tooltipContent}}
          unit="°C"
          withDots={false}
          withLegend={true}
          withRightYAxis={!!rightYAxisProps}
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
            type: "number",
          }}
          yAxisLabel={yAxisProps?.label}
          yAxisProps={{
            allowDataOverflow: yAxisProps?.allowDataOverflow,
            axisLine: false,
            domain: yAxisProps?.domain,
            tickCount: yAxisProps?.tickCount,
            tickFormatter: roundingTickLabelFormatter,
            tickMargin: 0,
            type: "number",
          }}
          rightYAxisLabel={rightYAxisProps?.label}
          rightYAxisProps={{
            allowDataOverflow: rightYAxisProps?.allowDataOverflow,
            axisLine: false,
            domain: rightYAxisProps?.domain,
            tickCount: rightYAxisProps?.tickCount,
            tickFormatter: roundingTickLabelFormatter,
            tickMargin: 0,
            type: "number"
          }}
      />
  )
}

export default GenericTemperatureChart;

const createTemperatureTooltip = (series: SeriesItem[]) => {
  const TemperatureTooltip = ({payload, label}: TooltipContentProps) => (
      <ChartTooltip
          label={tooltipLabelFormatter(label)}
          payload={payload}
          series={series}
      />
  );
  TemperatureTooltip.displayName = 'TemperatureTooltip';
  return TemperatureTooltip;
};
