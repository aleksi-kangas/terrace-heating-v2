'use server';
import {TemperatureSnapshot} from "@/app/types/snapshot";
import {baseUrl} from "@/app/api/heat-pump/api";

const temperaturesBaseUrl: string = `${baseUrl}/heat-pump/compressor`;

export const fetchTemperatureSnapshot = async (): Promise<TemperatureSnapshot> => {
  const url: string = `${temperaturesBaseUrl}/`;
  const response: Response = await fetch(url);
  return await response.json();
}
