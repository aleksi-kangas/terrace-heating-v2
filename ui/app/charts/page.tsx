import ChartsPanel from "@/app/charts/components/ChartsPanel";
import {fetchHeatPumpSnapshotsTrailingDays} from "@/app/api/heat-pump/snapshots";
import {HeatPumpSnapshot} from "@/app/types/snapshot";

const ChartsPage = async () => {
  const trailingDays: number = 1;
  const heatPumpSnapshots: HeatPumpSnapshot[] = await fetchHeatPumpSnapshotsTrailingDays(trailingDays);
  return (
      <ChartsPanel initialHeatPumpSnapshots={heatPumpSnapshots}/>
  );
}

export default ChartsPage;
