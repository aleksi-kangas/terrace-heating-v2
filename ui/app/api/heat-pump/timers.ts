'use server';

import {TimerSchedule, TimerType} from "@/app/types/timer";
import {baseUrl} from "@/app/api/heat-pump/api";

const timersBaseUrl: string = `${baseUrl}/heat-pump/timers`;

export const fetchTimerSchedule = async (timerType: TimerType): Promise<TimerSchedule> => {
  const url: string = `${timersBaseUrl}?timerType=${TimerType[timerType]}`;
  const response: Response = await fetch(url);
  return await response.json();
}

export const putTimerSchedule = async (timerType: TimerType, timerSchedule: TimerSchedule): Promise<TimerSchedule> => {
  const url: string = `${timersBaseUrl}`;
  const response: Response = await fetch(url, {
    method: 'PUT',
    headers: {
      'Content-type': 'application/json',
    },
    body: JSON.stringify({
      timerType: timerType,
      timerSchedule: timerSchedule,
    })
  });
  return await response.json();
}
