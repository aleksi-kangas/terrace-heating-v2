'use server';

import {TimerSchedule, TimerType} from "@/app/types/timer";

const baseUrl: string = "http://localhost:8080/heat-pump/timers"

export const fetchTimerSchedule = async (timerType: TimerType): Promise<TimerSchedule> => {
  const url: string = `${baseUrl}?timerType=${TimerType[timerType]}`;
  const response: Response = await fetch(url);
  return await response.json();
}

export const putTimerSchedule = async (timerType: TimerType, timerSchedule: TimerSchedule): Promise<TimerSchedule> => {
  const url: string = `${baseUrl}`;
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
