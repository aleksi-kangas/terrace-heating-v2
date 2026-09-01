package com.github.aleksikangas.backend.domain.heating;

public record HeatingState(HeatingMode mode,
                           boolean timersInUse) {

}
