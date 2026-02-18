import {Divider} from '@mantine/core'
import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {fetchHeatPumpSnapshotsTrailingDays} from "@/app/api/heat-pump/snapshots";
import TemperatureChart from "@/app/dashboard/@charts/components/temperature-chart";

const ChartsPage = async () => {
  const trailingDays: number = 2;
  const heatPumpSnapshots: HeatPumpSnapshot[] = await fetchHeatPumpSnapshotsTrailingDays(trailingDays);
  return (
      <>
        <TemperatureChart heatPumpSnapshots={heatPumpSnapshots} xAxisDomainTrailingDays={trailingDays} series={indoorSeries} />
        <Divider my="md"/>
        <TemperatureChart heatPumpSnapshots={heatPumpSnapshots} xAxisDomainTrailingDays={trailingDays} series={outdoorSeries}/>
      </>
  )
}

export default ChartsPage;

const indoorSeries = {
  name: "temperatureSnapshot.indoorC",
  label: "Indoor",
}

const outdoorSeries = {
  name: "temperatureSnapshot.outdoorC",
  label: "Outdoor",
}
