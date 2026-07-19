'use server';

import {CompressorDutyCycle} from "@/app/types/compressor";
import {baseUrl} from "@/app/api/heat-pump/api";

const compressorBaseUrl: string = `${baseUrl}/heat-pump/compressor`;

export const fetchCompressorDutyCyclesTrailingDays = async (days: number): Promise<CompressorDutyCycle[]> => {
  const url: string = `${compressorBaseUrl}/days/${days}`;
  const response: Response = await fetch(url);
  return await response.json();
}
