export interface TimerSchedule {
  mondaySchedule: WeekdaySchedule;
  tuesdaySchedule: WeekdaySchedule;
  wednesdaySchedule: WeekdaySchedule;
  thursdaySchedule: WeekdaySchedule;
  fridaySchedule: WeekdaySchedule;
  saturdaySchedule: WeekdaySchedule;
  sundaySchedule: WeekdaySchedule;
}

export const getWeekdaySchedule = (
    timerSchedule: TimerSchedule,
    weekday: Weekday
): WeekdaySchedule => {
  return timerSchedule[WEEKDAY_KEYS[weekday]];
};

export enum TimerType {
  HEAT_DISTRIBUTION_CIRCUIT_1,
  HEAT_DISTRIBUTION_CIRCUIT_2,
  HEAT_DISTRIBUTION_CIRCUIT_3,
  LOWER_STORAGE_TANK,
  UPPER_STORAGE_TANK
}

export enum Weekday {
  MONDAY,
  TUESDAY,
  WEDNESDAY,
  THURSDAY,
  FRIDAY,
  SATURDAY,
  SUNDAY
}

export const WEEKDAY_KEYS: Record<Weekday, keyof TimerSchedule> = {
  [Weekday.MONDAY]: "mondaySchedule",
  [Weekday.TUESDAY]: "tuesdaySchedule",
  [Weekday.WEDNESDAY]: "wednesdaySchedule",
  [Weekday.THURSDAY]: "thursdaySchedule",
  [Weekday.FRIDAY]: "fridaySchedule",
  [Weekday.SATURDAY]: "saturdaySchedule",
  [Weekday.SUNDAY]: "sundaySchedule",
};

export interface WeekdaySchedule {
  startHour: number;
  endHour: number;
  temperatureDeltaC: number;
}
