package com.github.aleksikangas.backend.controller.heatpump.heating;

import com.github.aleksikangas.backend.domain.heating.HeatingState;
import com.github.aleksikangas.backend.heatpump.heating.HeatPumpHeatingService;
import com.google.errorprone.annotations.ThreadSafe;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@RequestMapping("heat-pump/heating")
@RestController
@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
@ThreadSafe
public final class HeatPumpHeatingController {

  private final HeatPumpHeatingService heatPumpHeatingService;

  public HeatPumpHeatingController(@Autowired final HeatPumpHeatingService heatPumpHeatingService) {
    this.heatPumpHeatingService = Objects.requireNonNull(heatPumpHeatingService);
  }

  @GetMapping
  public ResponseEntity<HeatingState> getHeatingState() {
    return ResponseEntity.ok(heatPumpHeatingService.getHeatingState());
  }

  @PostMapping
  public ResponseEntity<HeatingState> updateHeatingState(@RequestBody final HeatingState heatingState) {
    return ResponseEntity.ok(heatPumpHeatingService.setHeatingState(heatingState));
  }
}
