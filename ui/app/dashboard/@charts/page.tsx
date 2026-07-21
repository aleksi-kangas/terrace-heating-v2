import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {fetchHeatPumpSnapshotsTrailingDays} from "@/app/api/heat-pump/snapshots";
import GenericTemperatureChart from "@/app/components/GenericTemperatureChart";

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
      <GenericTemperatureChart heatPumpSnapshots={heatPumpSnapshots} xAxisDomainTrailingDays={trailingDays}
                               series={temperatureSeries}/>
  )
}

export default DashboardChartsPage;

const temperatureSeries = [
  {
    name: "indoorC",
    label: "Indoor °C",
    color: "green"
  },
  {
    name: "outdoorC",
    label: "Outdoor °C",
    color: "blue"
  }
]
