import {Divider} from '@mantine/core'
import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {fetchCompressorDutyCyclesTrailingDays} from "@/app/api/heat-pump/compressor";
import {fetchHeatPumpSnapshotsTrailingDays} from "@/app/api/heat-pump/snapshots";
import GenericTemperatureChart from "@/app/components/GenericTemperatureChart";
import CompressorDutyCycleChart from "@/app/components/CompressorDutyCycleChart";

const DashboardChartsPage = async () => {
  const trailingDays: number = 2;
  const heatPumpSnapshots: HeatPumpSnapshot[] = await fetchHeatPumpSnapshotsTrailingDays(trailingDays);
  const compressorDutyCycles = await fetchCompressorDutyCyclesTrailingDays(trailingDays);
  return (
      <>
        <GenericTemperatureChart heatPumpSnapshots={heatPumpSnapshots} xAxisDomainTrailingDays={trailingDays}
                                 series={temperatureSeries}/>
        <Divider my="md"/>
        <CompressorDutyCycleChart compressorDutyCycles={compressorDutyCycles} xAxisDomainTrailingDays={trailingDays} />
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
