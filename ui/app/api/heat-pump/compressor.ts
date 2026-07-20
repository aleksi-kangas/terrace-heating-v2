'use server';

import {CompressorDutyCycle} from "@/app/types/compressor";
import {baseUrl} from "@/app/api/heat-pump/api";

const compressorBaseUrl: string = `${baseUrl}/heat-pump/compressor`;

export const fetchCompressorDutyCyclesTrailingDays = async (trailingDays: number, resolution: string = "PT1D"): Promise<CompressorDutyCycle[]> => {
  const response = await fetch(
      `${compressorBaseUrl}/days/${trailingDays}?resolution=${encodeURIComponent(resolution)}`
  );
  return response.json();
};
