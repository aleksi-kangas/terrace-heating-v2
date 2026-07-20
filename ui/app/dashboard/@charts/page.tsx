import {Divider} from '@mantine/core'
import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {fetchCompressorDutyCyclesTrailingDays} from "@/app/api/heat-pump/compressor";
import {fetchHeatPumpSnapshotsTrailingDays} from "@/app/api/heat-pump/snapshots";
import GenericTemperatureChart from "@/app/components/GenericTemperatureChart";
import CompressorDutyCycleChart from "@/app/components/CompressorDutyCycleChart";
import {parseResolution} from "@/app/types/compressor";

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
  const resolution = parseResolution(params.resolution);

  const heatPumpSnapshots: HeatPumpSnapshot[] = await fetchHeatPumpSnapshotsTrailingDays(trailingDays);
  const compressorDutyCycles = await fetchCompressorDutyCyclesTrailingDays(trailingDays, resolution);
  return (
      <>
        <GenericTemperatureChart heatPumpSnapshots={heatPumpSnapshots} xAxisDomainTrailingDays={trailingDays}
                                 series={temperatureSeries}/>
        <Divider my="md"/>
        <CompressorDutyCycleChart compressorDutyCycles={compressorDutyCycles} xAxisDomainTrailingDays={trailingDays}
                                  resolution={resolution}/>
      </>
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
