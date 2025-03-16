import {Divider} from '@mantine/core'
import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {fetchHeatPumpSnapshotsTrailingDays} from "@/app/api/heat-pump/snapshots";
import OutdoorTemperatureChart from "@/app/dashboard/@charts/components/outdoor-temperature-chart";

const ChartsPage = async () => {
  const trailingDays: number = 2;
  const heatPumpSnapshots: HeatPumpSnapshot[] = await fetchHeatPumpSnapshotsTrailingDays(trailingDays);
  return (
      <>
        <OutdoorTemperatureChart heatPumpSnapshots={heatPumpSnapshots} xAxisDomainTrailingDays={trailingDays}/>
        <Divider my="md"/>
        <OutdoorTemperatureChart heatPumpSnapshots={heatPumpSnapshots} xAxisDomainTrailingDays={trailingDays}/>
      </>
  )
}

export default ChartsPage;
