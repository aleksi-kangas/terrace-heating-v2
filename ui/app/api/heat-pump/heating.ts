'use server';
import {baseUrl} from "@/app/api/heat-pump/api";
import {HeatingState} from "@/app/types/heating";

const heatingBaseUrl: string = `${baseUrl}/heat-pump/heating`;

export const fetchHeatingState = async (): Promise<HeatingState> => {
  const url: string = `${heatingBaseUrl}`;
  const response: Response = await fetch(url);
  return await response.json();
}
