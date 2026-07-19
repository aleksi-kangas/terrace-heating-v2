export const dynamic = "force-dynamic";

import ChartsPanel from "@/app/charts/components/ChartsPanel";
import {fetchHeatPumpSnapshotsTrailingDays} from "@/app/api/heat-pump/snapshots";
import {HeatPumpSnapshot} from "@/app/types/snapshot";

interface ChartsPageProps {
  searchParams: Promise<{
    days?: string
  }>
}

const ChartsPage = async ({searchParams}: ChartsPageProps) => {
  const params = await searchParams;
  const trailingDays = Number(params.days ?? "1")
  const heatPumpSnapshots: HeatPumpSnapshot[] = await fetchHeatPumpSnapshotsTrailingDays(trailingDays);
  return (
      <ChartsPanel heatPumpSnapshots={heatPumpSnapshots} trailingDays={trailingDays}/>
  );
}

export default ChartsPage;
