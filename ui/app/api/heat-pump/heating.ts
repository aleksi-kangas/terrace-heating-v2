'use server';
import {baseUrl} from "@/app/api/heat-pump/api";
import {HeatingState} from "@/app/types/heating";

const heatingBaseUrl: string = `${baseUrl}/heat-pump/heating`;

export const fetchHeatingState = async (): Promise<HeatingState> => {
  const url: string = `${heatingBaseUrl}`;
  const response: Response = await fetch(url);
  return await response.json();
}

export const putHeatingState = async (heatingState: HeatingState): Promise<HeatingState> => {
  const url: string = `${heatingBaseUrl}`;
  const response: Response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-type': 'application/json',
    },
    body: JSON.stringify(heatingState)
  });
  if (!response.ok) {
    const message = await response.text();
    throw new Error(message);
  }
  return await response.json();
}
