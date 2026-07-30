package com.github.aleksikangas.backend.heatpump.heating;

import com.github.aleksikangas.backend.domain.heating.HeatingState;
import com.github.aleksikangas.backend.heatpump.client.HeatPumpClient;
import com.github.aleksikangas.backend.heatpump.client.HeatPumpClientException;
import com.google.errorprone.annotations.ThreadSafe;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
@Service
@ThreadSafe
public final class HeatPumpHeatingService {

  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  private final HeatPumpClient heatPumpClient;

  public HeatPumpHeatingService(@Autowired final HeatPumpClient heatPumpClient) {
    this.heatPumpClient = Objects.requireNonNull(heatPumpClient);
  }

  public HeatingState getHeatingState() {
    lock.readLock().lock();
    try {
      return HeatingState.of(heatPumpClient.readActiveHeatDistributionCircuitCount());
    } catch (final HeatPumpClientException e) {
      throw new HeatPumpHeatingException(e);
    } finally {
      lock.readLock().unlock();
    }
  }

  public HeatingState setHeatingState(final HeatingState newHeatingState) {
    final HeatingState heatingState = getHeatingState();
    if (heatingState == newHeatingState) {
      return newHeatingState;
    }
    lock.writeLock().lock();
    try {
      heatPumpClient.writeActiveHeatDistributionCircuitCount(newHeatingState.getActiveHeatDistributionCircuitCount());
    } catch (final HeatPumpClientException e) {
      throw new HeatPumpHeatingException(e);
    } finally {
      lock.writeLock().unlock();
    }
    return getHeatingState();
  }
}
