'use client';

import {useMemo} from 'react';
import {DateTime} from 'luxon';
import {BarChart, ChartTooltip} from '@mantine/charts';
import {Box, Group, SegmentedControl} from '@mantine/core';
import {usePathname, useRouter, useSearchParams} from 'next/navigation';
import {CompressorDutyCycle, Resolution, RESOLUTIONS} from '@/app/types/compressor';

interface CompressorDutyCycleChartProps {
  compressorDutyCycles: CompressorDutyCycle[];
  xAxisDomainTrailingDays: number;
  resolution: Resolution;
}

const CompressorDutyCycleChart = ({
                                    compressorDutyCycles,
                                    xAxisDomainTrailingDays,
                                    resolution,
                                  }: CompressorDutyCycleChartProps) => {
  const router = useRouter();
  const pathname = usePathname();
  const searchParams = useSearchParams();

  const data = useMemo(
      () =>
          compressorDutyCycles.map((dutyCycle) => {
            const start = DateTime.fromISO(dutyCycle.startTime);
            const end = DateTime.fromISO(dutyCycle.endTime);
            return {
              timestamp: (start.toSeconds() + end.toSeconds()) / 2,
              load: dutyCycle.load * 100,
              activeCount: dutyCycle.activeCount,
              count: dutyCycle.count,
            };
          }),
      [compressorDutyCycles]
  );
  const xAxisDomain = useMemo(() => {
    const now = DateTime.now();
    return [
      now.minus({days: xAxisDomainTrailingDays}).toSeconds(),
      now.toSeconds(),
    ];
  }, [xAxisDomainTrailingDays]);

  const handleResolutionChange = (value: string) => {
    const params = new URLSearchParams(searchParams.toString());
    params.set("resolution", value);
    router.replace(`${pathname}?${params}`, {
      scroll: false,
    });
  };

  return (
      <Box h="100%" p="md">
        <BarChart
            data={data}
            dataKey="timestamp"
            h="95%"
            series={[
              {
                name: 'load',
                label: 'Compressor %',
                color: 'blue',
              },
            ]}
            withBarValueLabel={true}
            withLegend={true}
            xAxisProps={{
              angle: -60,
              axisLine: false,
              domain: xAxisDomain,
              height: 50,
              tickFormatter: tickLabelFormatter,
              type: 'number',
            }}
            yAxisProps={{
              domain: [0, 100],
            }}
            tooltipProps={{
              content: ({label, payload}) => (
                  <ChartTooltip
                      label={tooltipLabelFormatter(label)}
                      payload={payload}
                      series={[
                        {
                          name: 'load',
                          label: 'Compressor %',
                          color: 'blue',
                        },
                      ]}
                  />
              ),
            }}
        />
        <Group h="5%" justify="center" mt="md">
          <SegmentedControl
              value={resolution}
              onChange={handleResolutionChange}
              data={[...RESOLUTIONS]}
          />
        </Group>
      </Box>
  );
};

export default CompressorDutyCycleChart;

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
