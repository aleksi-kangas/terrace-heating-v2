/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.domain.timer;

import jakarta.validation.Valid;

public record TimerSchedule(@Valid WeekdaySchedule mondaySchedule,
                            @Valid WeekdaySchedule tuesdaySchedule,
                            @Valid WeekdaySchedule wednesdaySchedule,
                            @Valid WeekdaySchedule thursdaySchedule,
                            @Valid WeekdaySchedule fridaySchedule,
                            @Valid WeekdaySchedule saturdaySchedule,
                            @Valid WeekdaySchedule sundaySchedule) {

  public WeekdaySchedule getWeekdaySchedule(final Weekday weekday) {
    return switch (weekday) {
      case MONDAY -> mondaySchedule;
      case TUESDAY -> tuesdaySchedule;
      case WEDNESDAY -> wednesdaySchedule;
      case THURSDAY -> thursdaySchedule;
      case FRIDAY -> fridaySchedule;
      case SATURDAY -> saturdaySchedule;
      case SUNDAY -> sundaySchedule;
    };
  }
}
