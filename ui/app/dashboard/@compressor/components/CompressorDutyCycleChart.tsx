'use client';

import {useMemo, useTransition} from 'react';
import {DateTime} from 'luxon';
import {BarChart, ChartTooltip} from '@mantine/charts';
import {Box, LoadingOverlay} from '@mantine/core';
import {usePathname, useRouter, useSearchParams} from 'next/navigation';
import {CompressorDutyCycle, Resolution, RESOLUTIONS} from '@/app/types/compressor';
import ResolutionSelection from "@/app/dashboard/@compressor/components/CompresorDutyCycleResolutionSelection";
import {useMediaQuery} from "@mantine/hooks";
import {tickLabelFormatter, tooltipDatetimeRangeFormatter} from "@/app/utils/chart-formatters";
import {TooltipContentProps} from "recharts";

interface XAxisProps {
  domainTrailingDays: number;
}

interface CompressorDutyCycleChartProps {
  compressorDutyCycles: CompressorDutyCycle[];
  resolution: Resolution;
  xAxisProps: XAxisProps;
}

const DutyCycleTooltip = ({payload}: TooltipContentProps) => {
  const point = payload?.[0]?.payload;
  return (
      <ChartTooltip
          label={tooltipDatetimeRangeFormatter(point?.startTime, point?.endTime)}
          payload={payload}
          series={[
            {
              name: 'load',
              label: 'Compressor %',
              color: 'blue',
            },
          ]}
      />
  )
};

const CompressorDutyCycleChart = ({
                                    compressorDutyCycles,
                                    resolution,
                                    xAxisProps
                                  }: CompressorDutyCycleChartProps) => {
  const isMobile = useMediaQuery("(max-width: 48em)");
  const router = useRouter();
  const pathname = usePathname();
  const searchParams = useSearchParams();
  const [isPending, startTransition] = useTransition();

  const data = useMemo(() =>
          compressorDutyCycles.map((dutyCycle) => {
            const start = DateTime.fromISO(dutyCycle.startTime);
            const end = DateTime.fromISO(dutyCycle.endTime);
            return {
              timestamp: (start.toSeconds() + end.toSeconds()) / 2,
              startTime: start,
              endTime: end,
              load: Math.round(dutyCycle.load * 100),
              activeCount: dutyCycle.activeCount,
              count: dutyCycle.count,
            };
          }),
      [compressorDutyCycles]
  );

  const xAxisDomain = useMemo(() => {
    const now: DateTime = DateTime.now();
    return [now.minus({days: xAxisProps.domainTrailingDays}).toSeconds(), now.toSeconds()]
  }, [xAxisProps.domainTrailingDays]);

  const xAxisTickInterval = useMemo(() => {
    const days = xAxisProps.domainTrailingDays;
    if (days <= 1) return isMobile ? 4 : 0;
    if (days <= 2) return isMobile ? 5 : 1;
    if (days <= 3) return isMobile ? 6 : 2;
    return isMobile ? 8 : 3;
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

  const handleResolutionChange = (value: string) => {
    startTransition(() => {
      const params = new URLSearchParams(searchParams.toString());
      params.set('resolution', value);
      router.replace(`${pathname}?${params}`, {scroll: false});
    });
  };

  return (
      <Box h="100%" p="0" style={{display: "flex", flexDirection: "column"}}>
        <LoadingOverlay
            visible={isPending}
            zIndex={10}
            overlayProps={{radius: 'sm', blur: 1}}
        />
        <Box p="0" style={{flex: 1, minHeight: 0}}>
          <BarChart
              barProps={{radius: [10, 10, 0, 0]}}
              data={data}
              dataKey="timestamp"
              h="90%"
              series={[
                {
                  name: 'load',
                  label: 'Compressor %',
                  color: 'blue',
                },
              ]}
              tooltipProps={{content: DutyCycleTooltip}}
              tickLine="xy"
              withBarValueLabel={true}
              withLegend={true}
              xAxisProps={{
                angle: -60,
                axisLine: false,
                domain: xAxisDomain,
                height: 40,
                interval: xAxisTickInterval,
                tickFormatter: (v) => tickLabelFormatter(v, isMobile),
                tickMargin: 30,
                ticks: xAxisTicks,
                type: 'number',
              }}
              yAxisProps={{
                domain: [0, 100],
                interval: 0,
                ticks: isMobile ? [0, 20, 40, 60, 80, 100] : [0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
              }}
          />
        </Box>
        <Box p="0">
          <ResolutionSelection
              selection={resolution}
              onSelectionChange={handleResolutionChange}
              values={[...RESOLUTIONS]}
              disabled={isPending}
          />
        </Box>
      </Box>
  );
};

export default CompressorDutyCycleChart;
