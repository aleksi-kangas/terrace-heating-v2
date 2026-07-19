'use server';
import {DateTime} from 'luxon';
import {HeatPumpSnapshot} from "@/app/types/snapshot";
import {URLSearchParams} from "url";
import {baseUrl} from "@/app/api/heat-pump/api";

const snapshotsBaseUrl: string = `${baseUrl}/heat-pump/snapshots`;

export const fetchHeatPumpSnapshotsBetween = async (from: DateTime, to: DateTime): Promise<HeatPumpSnapshot[]> => {
  const url: string = `${snapshotsBaseUrl}?` + new URLSearchParams({from: from.toISO()!, to: to.toISO()!});
  const response: Response = await fetch(url);
  return await response.json();
}

export const fetchHeatPumpSnapshotsTrailingDays = async (days: number): Promise<HeatPumpSnapshot[]> => {
  const url: string = `${snapshotsBaseUrl}/days/${days}`;
  const response: Response = await fetch(url);
  return await response.json();
}
