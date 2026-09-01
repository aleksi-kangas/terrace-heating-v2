/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client;

import com.github.aleksikangas.backend.domain.snapshot.TemperatureSnapshot;
import com.github.aleksikangas.backend.domain.timer.TimerSchedule;
import com.github.aleksikangas.backend.domain.timer.TimerType;
import com.github.aleksikangas.backend.heatpump.client.coils.HeatingCoils;
import com.github.aleksikangas.backend.heatpump.client.parsers.TemperatureSnapshotParser;
import com.github.aleksikangas.backend.heatpump.client.parsers.TimerScheduleParser;
import com.github.aleksikangas.backend.heatpump.client.registers.HeatingRegisters;
import com.github.aleksikangas.backend.heatpump.client.registers.RegisterRange;
import com.github.aleksikangas.backend.heatpump.client.registers.TemperatureRegisters;
import com.github.aleksikangas.backend.heatpump.client.registers.TimerRegisters;
import com.github.aleksikangas.backend.heatpump.client.utils.RegisterUtils;
import com.google.errorprone.annotations.ThreadSafe;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.SortedMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.solarnetwork.io.modbus.ModbusClient;
import net.solarnetwork.io.modbus.ModbusException;
import net.solarnetwork.io.modbus.netty.msg.BitsModbusMessage;
import net.solarnetwork.io.modbus.netty.msg.RegistersModbusMessage;
import net.solarnetwork.io.modbus.tcp.TcpModbusClientConfig;
import net.solarnetwork.io.modbus.tcp.netty.NettyTcpModbusClientConfig;
import net.solarnetwork.io.modbus.tcp.netty.TcpNettyModbusClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * A Modbus TCP implementation of {@link HeatPumpClient}.
 */
@Service
@ThreadSafe
public class HeatPumpTcpClient implements HeatPumpClient {

  private static final TcpModbusClientConfig CONFIG = new NettyTcpModbusClientConfig(System.getenv("HEAT_PUMP_IP"),
      Integer.parseInt(System.getenv("HEAT_PUMP_PORT")));
  private static final Logger LOG = LoggerFactory.getLogger(HeatPumpTcpClient.class);
  private static final int UNIT_ID = 1;

  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  @GuardedBy("readWriteLock")
  private final ModbusClient client = new TcpNettyModbusClient(CONFIG);

  @PostConstruct
  private void postConstruct() {
    try {
      client.start().get();
      LOG.debug("Modbus client started successfully");
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new HeatPumpClientException(e);
    } catch (final ExecutionException e) {
      throw new HeatPumpClientException(e);
    }
  }

  @PreDestroy
  private void preDestroy() {
    LOG.debug("Stopping Modbus client");
    client.stop();
  }

  @Override
  public short readActiveHeatDistributionCircuitCount() {
    try {
      readWriteLock.readLock().lock();
      final short[] registerValues = readHoldingRegisterRange(
          RegisterRange.of(HeatingRegisters.ACTIVE_HEAT_DISTRIBUTION_CIRCUIT_COUNT));
      if (registerValues.length != 1) {
        throw new HeatPumpClientException("Failed to read active heat distribution circuit count");
      }
      return registerValues[0];
    } catch (final ModbusException e) {
      throw new HeatPumpClientException(e);
    } finally {
      readWriteLock.readLock().unlock();
    }
  }

  @Override
  public void writeActiveHeatDistributionCircuitCount(final short activeHeatDistributionCircuitCount) {
    try {
      readWriteLock.writeLock().lock();
      writeHoldingRegisterRange(RegisterRange.of(HeatingRegisters.ACTIVE_HEAT_DISTRIBUTION_CIRCUIT_COUNT),
          new short[]{activeHeatDistributionCircuitCount});
    } catch (final ModbusException e) {
      throw new HeatPumpClientException(e);
    } finally {
      readWriteLock.writeLock().unlock();
    }
  }

  @Override
  public TemperatureSnapshot readTemperatureSnapshot() {
    try {
      readWriteLock.readLock().lock();
      final short[] temperatureValues = readHoldingRegisterRange(TemperatureRegisters.getRegisterRange());
      return TemperatureSnapshotParser.parse(temperatureValues);
    } catch (final ModbusException e) {
      throw new HeatPumpClientException(e);
    } finally {
      readWriteLock.readLock().unlock();
    }
  }

  @Override
  public boolean readTimersInUse() {
    try {
      readWriteLock.readLock().lock();
      return readCoil(HeatingCoils.TIMERS_IN_USE);
    } catch (final ModbusException e) {
      throw new HeatPumpClientException(e);
    } finally {
      readWriteLock.readLock().unlock();
    }
  }

  @Override
  public void writeTimersInUse(final boolean timersInUse) {
    try {
      readWriteLock.writeLock().lock();
      writeCoil(HeatingCoils.TIMERS_IN_USE, timersInUse);
    } catch (final ModbusException e) {
      throw new HeatPumpClientException(e);
    } finally {
      readWriteLock.writeLock().unlock();
    }
  }

  @Override
  public TimerSchedule readTimerSchedule(final TimerType timerType) {
    try {
      readWriteLock.readLock().lock();
      final TimerRegisters timerRegisters = TimerRegisters.of(timerType);
      final RegisterRange startEndHourRegisterRange = timerRegisters.getStartEndHourRegisterRange();
      final RegisterRange temperatureDeltaRegisterRange = timerRegisters.getTemperatureDeltaRegisterRange();
      final short[] startHourEndHourValues = readHoldingRegisterRange(startEndHourRegisterRange);
      final short[] temperatureDeltaValues = readHoldingRegisterRange(temperatureDeltaRegisterRange);
      return TimerScheduleParser.parse(timerType, startHourEndHourValues, temperatureDeltaValues);
    } catch (final ModbusException e) {
      throw new HeatPumpClientException(e);
    } finally {
      readWriteLock.readLock().unlock();
    }
  }

  /**
   * Write the given {@link TimerSchedule} as the current of the given {@link TimerType}.
   *
   * @param timerType     of interest
   * @param timerSchedule to write
   * @throws HeatPumpClientException on failure
   * @implNote Implemented as multiple write operations, each writing contiguous address ranges at once. The overall
   * write operations are not transacted, i.e. may partially fail. If any of the writing operations fails, the overall
   * operation is not continued, and the resulting state shall equal to the intersection of the original state and the
   * succeeded write operations.
   */
  @Override
  public void writeTimerSchedule(final TimerType timerType, final TimerSchedule timerSchedule) {
    try {
      readWriteLock.writeLock().lock();
      final SortedMap<Integer, Short> registerValueMap = RegisterUtils.buildRegisterValueMap(timerType, timerSchedule);
      RegisterUtils.extractContiguousRegisterValueRanges(registerValueMap)
          .forEach(c -> writeHoldingRegisterRange(c.registerRange(), c.values()));
    } catch (final ModbusException e) {
      throw new HeatPumpClientException(e);
    } finally {
      readWriteLock.writeLock().unlock();
    }
  }

  private short[] readHoldingRegisterRange(final RegisterRange registerRange) {
    final RegistersModbusMessage request = RegistersModbusMessage.readHoldingsRequest(UNIT_ID,
        registerRange.startRegister(), registerRange.registerCount());
    final var registersModbusMessage = client.send(request).unwrap(RegistersModbusMessage.class);
    if (registersModbusMessage == null) {
      throw new ModbusException("Failed to unwrap Modbus message as RegistersModbusMessage");
    }
    final short[] data = registersModbusMessage.dataDecode();
    return data != null ? data : new short[0];
  }

  private void writeHoldingRegisterRange(final RegisterRange registerRange, final short[] registerValues)
      throws ModbusException {
    final var request = RegistersModbusMessage.writeHoldingsRequest(UNIT_ID,
        registerRange.startRegister(),
        registerValues);
    client.send(request);
  }

  private boolean readCoil(final int coilAddress) {
    final var request = BitsModbusMessage.readCoilsRequest(UNIT_ID, coilAddress, 1);
    final var bitsModbusMessageResponse = client.send(request).unwrap(BitsModbusMessage.class);
    if (bitsModbusMessageResponse == null) {
      throw new ModbusException("Failed to unwrap Modbus message as BitsModbusMessage");
    }
    return bitsModbusMessageResponse.isBitEnabled(0);
  }

  private void writeCoil(final int coilAddress, final boolean value) {
    final var request = BitsModbusMessage.writeCoilRequest(UNIT_ID, coilAddress, value);
    client.send(request);
  }
}
