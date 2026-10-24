import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {fetchHeatPumpSnapshotsTrailingDays} from "@/app/api/heat-pump/snapshots";
import GenericTemperatureChart, {SeriesItem} from "@/app/components/GenericTemperatureChart";
import {Box} from "@mantine/core";

const DEFAULT_TRAILING_DAYS = 2;

interface DashboardChartsPageProps {
  searchParams: Promise<{
    trailingDays?: string;
    resolution?: string;
  }>;
}

const DashboardChartsPage = async ({searchParams}: DashboardChartsPageProps) => {
  const params = await searchParams;

  const trailingDays = Number(params.trailingDays ?? DEFAULT_TRAILING_DAYS.toString())

  const heatPumpSnapshots: HeatPumpSnapshot[] = await fetchHeatPumpSnapshotsTrailingDays(trailingDays);
  return (
      <Box h="100%" p="xs">
        <GenericTemperatureChart heatPumpSnapshots={heatPumpSnapshots} series={temperatureSeries}
                                 xAxisProps={{domainTrailingDays: trailingDays}}
                                 yAxisProps={{
                                   domain: ["auto", "auto"],
                                   label: "Outdoor",
                                   tickCount: 10,
                                 }}
                                 rightYAxisProps={{
                                   domain: ["auto", "auto"],
                                   label: "Indoor",
                                   tickCount: 10,
                                 }}/>
      </Box>
  )
}

export default DashboardChartsPage;

const temperatureSeries: SeriesItem[] = [
  {
    name: "indoorC",
    label: "Indoor °C",
    color: "green",
    yAxisId: "right"
  },
  {
    name: "outdoorC",
    label: "Outdoor °C",
    color: "blue"
  }
]
