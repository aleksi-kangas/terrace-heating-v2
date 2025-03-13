export interface TimerSchedule {
  mondaySchedule: WeekdaySchedule;
  tuesdaySchedule: WeekdaySchedule;
  wednesdaySchedule: WeekdaySchedule;
  thursdaySchedule: WeekdaySchedule;
  fridaySchedule: WeekdaySchedule;
  saturdaySchedule: WeekdaySchedule;
  sundaySchedule: WeekdaySchedule;
}

export const getWeekdaySchedule = (timerSchedule: TimerSchedule, weekday: Weekday): WeekdaySchedule => {
  switch (weekday) {
    case Weekday.MONDAY:
      return timerSchedule.mondaySchedule;
    case Weekday.TUESDAY:
      return timerSchedule.tuesdaySchedule;
    case Weekday.WEDNESDAY:
      return timerSchedule.wednesdaySchedule;
    case Weekday.THURSDAY:
      return timerSchedule.thursdaySchedule;
    case Weekday.FRIDAY:
      return timerSchedule.fridaySchedule;
    case Weekday.SATURDAY:
      return timerSchedule.saturdaySchedule;
    case Weekday.SUNDAY:
      return timerSchedule.sundaySchedule;
  }
}

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

export interface WeekdaySchedule {
  startHour: number;
  endHour: number;
  temperatureDeltaC: number;
}
