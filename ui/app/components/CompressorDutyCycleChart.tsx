'use client';

import {useMemo} from 'react';
import {DateTime} from 'luxon';
import {BarChart, ChartTooltip} from '@mantine/charts';
import {CompressorDutyCycle} from '@/app/types/compressor';
import {Box} from "@mantine/core";

interface CompressorDutyCycleChartProps {
  compressorDutyCycles: CompressorDutyCycle[];
  xAxisDomainTrailingDays: number;
}

const CompressorDutyCycleChart = ({
                                    compressorDutyCycles,
                                    xAxisDomainTrailingDays,
                                  }: CompressorDutyCycleChartProps) => {

  const data = useMemo(() =>
          compressorDutyCycles.map(compressorDutyCycle => ({
            timestamp: DateTime.fromISO(compressorDutyCycle.startTime).toSeconds(),
            dutyCycle: Math.round(compressorDutyCycle.load * 100),
            activeCount: compressorDutyCycle.activeCount,
            count: compressorDutyCycle.count,
          })),
      [compressorDutyCycles]
  );

  const xAxisDomain = useMemo(() => {
    const now = DateTime.now();
    return [now.minus({days: xAxisDomainTrailingDays}).toSeconds(), now.toSeconds()];
  }, [xAxisDomainTrailingDays]);

  return (
      <Box h="100%" p="md">
        <BarChart
            data={data}
            dataKey="timestamp"
            h="100%"
            series={[
              {
                name: 'dutyCycle',
                label: 'Compressor %',
                color: 'blue',
              },
            ]}
            type="percent"
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
            tooltipProps={{
              content: ({label, payload}) => (
                  <ChartTooltip
                      label={tooltipLabelFormatter(label)}
                      payload={payload}
                      series={[
                        {
                          name: 'dutyCycle',
                          label: 'Compressor %',
                          color: 'blue',
                        },
                      ]}
                  />
              ),
            }}
        />
      </Box>
  );
}

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
