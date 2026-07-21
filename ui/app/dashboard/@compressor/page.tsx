import {fetchCompressorDutyCyclesTrailingDays} from "@/app/api/heat-pump/compressor";
import CompressorDutyCycleChart from "@/app/components/CompressorDutyCycleChart";
import {parseResolution} from "@/app/types/compressor";

const DEFAULT_TRAILING_DAYS = 2;

interface DashboardCompressorPageProps {
  searchParams: Promise<{
    trailingDays?: string;
    resolution?: string;
  }>;
}

const DashboardCompressorPage = async ({searchParams}: DashboardCompressorPageProps) => {
  const params = await searchParams;

  const trailingDays = Number(params.trailingDays ?? DEFAULT_TRAILING_DAYS.toString())
  const resolution = parseResolution(params.resolution);

  const compressorDutyCycles = await fetchCompressorDutyCyclesTrailingDays(trailingDays, resolution);
  return (
      <CompressorDutyCycleChart compressorDutyCycles={compressorDutyCycles} xAxisDomainTrailingDays={trailingDays}
                                resolution={resolution}/>
  )
}

export default DashboardCompressorPage;
