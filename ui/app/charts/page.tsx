export const dynamic = "force-dynamic";

import ChartsPanel from "@/app/charts/components/ChartsPanel";
import {fetchHeatPumpSnapshotsTrailingDays} from "@/app/api/heat-pump/snapshots";

interface ChartsPageProps {
  searchParams: Promise<{
    days?: string
  }>
}

const ChartsPage = async ({searchParams}: ChartsPageProps) => {
  const params = await searchParams;
  const daysNumber = Number(params.days);
  const trailingDays = Number.isFinite(daysNumber) && daysNumber > 0 ? Math.min(daysNumber, 30) : 1;
  const heatPumpSnapshots = await fetchHeatPumpSnapshotsTrailingDays(trailingDays);
  return (
      <ChartsPanel heatPumpSnapshots={heatPumpSnapshots} trailingDays={trailingDays}/>
  );
}

export default ChartsPage;
