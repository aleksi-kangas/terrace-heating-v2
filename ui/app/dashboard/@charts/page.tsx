import {Divider} from '@mantine/core'
import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {fetchHeatPumpSnapshotsTrailingDays} from "@/app/api/heat-pump/snapshots";
import GenericTemperatureChart from "@/app/components/GenericTemperatureChart";

const ChartsPage = async () => {
  const trailingDays: number = 2;
  const heatPumpSnapshots: HeatPumpSnapshot[] = await fetchHeatPumpSnapshotsTrailingDays(trailingDays);
  return (
      <>
        <GenericTemperatureChart heatPumpSnapshots={heatPumpSnapshots} xAxisDomainTrailingDays={trailingDays}
                                 series={[indoorSeries]}/>
        <Divider my="md"/>
        <GenericTemperatureChart heatPumpSnapshots={heatPumpSnapshots} xAxisDomainTrailingDays={trailingDays}
                                 series={[outdoorSeries]}/>
      </>
  )
}

export default ChartsPage;

const indoorSeries = {
  name: "temperatureSnapshot.indoorC",
  label: "Indoor °C",
  color: "green"
}

const outdoorSeries = {
  name: "temperatureSnapshot.outdoorC",
  label: "Outdoor °C",
  color: "blue"
}
