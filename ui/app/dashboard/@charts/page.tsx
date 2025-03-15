import {Divider} from '@mantine/core'
import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {fetchHeatPumpSnapshotsTrailingDays} from "@/app/api/heat-pump/snapshots";
import OutdoorTemperatureChart from "@/app/dashboard/@charts/components/outdoor-temperature-chart";

const ChartsPage = async () => {
  const heatPumpSnapshots: HeatPumpSnapshot[] = await fetchHeatPumpSnapshotsTrailingDays(2);
  return (
      <>
        <OutdoorTemperatureChart heatPumpSnapshots={heatPumpSnapshots}/>
        <Divider my="md"/>
        <OutdoorTemperatureChart heatPumpSnapshots={heatPumpSnapshots}/>
      </>
  )
}

export default ChartsPage;
