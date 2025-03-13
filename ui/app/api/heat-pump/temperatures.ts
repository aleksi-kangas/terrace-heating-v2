'use server';
import {TemperatureSnapshot} from "@/app/types/snapshot";

const baseUrl: string = "http://localhost:8080/heat-pump/temperatures"

export const fetchTemperatureSnapshot = async (): Promise<TemperatureSnapshot> => {
  const url: string = `${baseUrl}/`;
  const response: Response = await fetch(url);
  return await response.json();
}
