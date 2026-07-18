import {Divider} from '@mantine/core'
import {HeatPumpSnapshot, TemperatureSnapshot} from "@/app/types/snapshot";
import {fetchHeatPumpSnapshotsTrailingDays} from "@/app/api/heat-pump/snapshots";
import GenericTemperatureChart from "@/app/components/GenericTemperatureChart";
import {DateTime} from "luxon";

const ChartsPage = async () => {
  const trailingDays: number = 2;
  const heatPumpSnapshots: HeatPumpSnapshot[] = await fetchHeatPumpSnapshotsTrailingDays(trailingDays);
  const timestamps: DateTime[] = heatPumpSnapshots.map(heatPumpSnapshot => DateTime.fromISO(heatPumpSnapshot.timestamp))
  const temperatureSnapshots: TemperatureSnapshot[] = heatPumpSnapshots.map(heatPumpSnapshot => heatPumpSnapshot.temperatureSnapshot);
  return (
      <>
        <GenericTemperatureChart timestamps={timestamps} temperatureSnapshots={temperatureSnapshots}
                                 xAxisDomainTrailingDays={trailingDays} series={[indoorSeries]}/>
        <Divider my="md"/>
        <GenericTemperatureChart timestamps={timestamps} temperatureSnapshots={temperatureSnapshots}
                                 xAxisDomainTrailingDays={trailingDays} series={[outdoorSeries]}/>
      </>
  )
}

export default ChartsPage;

const indoorSeries = {
  name: "indoorC",
  label: "Indoor °C",
  color: "green"
}

const outdoorSeries = {
  name: "outdoorC",
  label: "Outdoor °C",
  color: "blue"
}
