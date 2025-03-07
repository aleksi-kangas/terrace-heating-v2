/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.controller.heatpump.timer;

import com.github.aleksikangas.backend.domain.timer.TimerSchedule;
import com.github.aleksikangas.backend.domain.timer.TimerType;
import com.github.aleksikangas.backend.heatpump.client.HeatPumpClient;
import com.github.aleksikangas.backend.heatpump.client.HeatPumpClientException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("heat-pump/timers")
@RestController
public final class HeatPumpTimerController {

  private final HeatPumpClient heatPumpClient;

  public HeatPumpTimerController(@Autowired final HeatPumpClient heatPumpClient) {
    this.heatPumpClient = heatPumpClient;
  }

  @GetMapping
  public ResponseEntity<TimerSchedule> getTimerSchedule(@RequestParam final TimerType timerType) {
    try {
      return ResponseEntity.ok(heatPumpClient.readTimerSchedule(timerType));
    } catch (final HeatPumpClientException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PutMapping
  public ResponseEntity<TimerSchedule> updateTimerSchedule(
      @Valid @RequestBody final UpdateTimerScheduleRequest request) {
    try {
      heatPumpClient.writeTimerSchedule(request.timerType(), request.timerSchedule());
      return ResponseEntity.ok(heatPumpClient.readTimerSchedule(request.timerType()));
    } catch (final HeatPumpClientException e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
