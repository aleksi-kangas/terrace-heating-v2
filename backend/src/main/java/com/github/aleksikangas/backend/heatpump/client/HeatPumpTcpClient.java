/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client;

import com.github.aleksikangas.backend.domain.snapshot.TemperatureSnapshot;
import com.github.aleksikangas.backend.domain.timer.TimerSchedule;
import com.github.aleksikangas.backend.domain.timer.TimerType;
import com.github.aleksikangas.backend.heatpump.client.parsers.TemperatureSnapshotParser;
import com.github.aleksikangas.backend.heatpump.client.parsers.TimerScheduleParser;
import com.github.aleksikangas.backend.heatpump.client.registers.RegisterRange;
import com.github.aleksikangas.backend.heatpump.client.registers.TemperatureRegisters;
import com.github.aleksikangas.backend.heatpump.client.registers.TimerRegisters;
import com.github.aleksikangas.backend.heatpump.client.utils.RegisterUtils;
import java.util.SortedMap;
import java.util.concurrent.ExecutionException;
import net.solarnetwork.io.modbus.ModbusClient;
import net.solarnetwork.io.modbus.ModbusException;
import net.solarnetwork.io.modbus.netty.msg.RegistersModbusMessage;
import net.solarnetwork.io.modbus.tcp.TcpModbusClientConfig;
import net.solarnetwork.io.modbus.tcp.netty.NettyTcpModbusClientConfig;
import net.solarnetwork.io.modbus.tcp.netty.TcpNettyModbusClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * A Modbus TCP implementation of {@link HeatPumpClient}.
 */
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Service
public class HeatPumpTcpClient implements HeatPumpClient {

  private static final TcpModbusClientConfig CONFIG = new NettyTcpModbusClientConfig(
      System.getenv("HEAT_PUMP_IP"),
      Integer.parseInt(System.getenv("HEAT_PUMP_PORT")));
  private static final Logger LOG = LoggerFactory.getLogger(HeatPumpTcpClient.class);
  private static final int UNIT_ID = 1;

  private final ModbusClient client = new TcpNettyModbusClient(CONFIG);

  @Override
  public TemperatureSnapshot readTemperatureSnapshot() throws HeatPumpClientException {
    try {
      client.start().get();
      final short[] temperatureValues = readHoldingRegisterRange(TemperatureRegisters.getRegisterRange());
      return TemperatureSnapshotParser.parse(temperatureValues);
    } catch (final ExecutionException | InterruptedException | ModbusException e) {
      LOG.error("Failed to read temperature snapshot", e);
      throw new HeatPumpClientException(e);
    } finally {
      client.stop();
    }
  }

  @Override
  public TimerSchedule readTimerSchedule(final TimerType timerType) throws HeatPumpClientException {
    try {
      client.start().get();
      final TimerRegisters timerRegisters = TimerRegisters.of(timerType);
      final RegisterRange startEndHourRegisterRange = timerRegisters.getStartEndHourRegisterRange();
      final RegisterRange temperatureDeltaRegisterRange = timerRegisters.getTemperatureDeltaRegisterRange();
      final short[] startHourEndHourValues = readHoldingRegisterRange(startEndHourRegisterRange);
      final short[] temperatureDeltaValues = readHoldingRegisterRange(temperatureDeltaRegisterRange);
      return TimerScheduleParser.parse(timerType, startHourEndHourValues, temperatureDeltaValues);
    } catch (final ExecutionException | InterruptedException | ModbusException e) {
      LOG.error("Failed to read timer schedule", e);
      throw new HeatPumpClientException(e);
    } finally {
      client.stop();
    }
  }

  /**
   * Write the given {@link TimerSchedule} as the current of the given {@link TimerType}.
   *
   * @param timerType     of interest
   * @param timerSchedule to write
   * @throws HeatPumpClientException on failure
   * @implNote Implemented as multiple write operations, each writing contiguous address ranges at once. The overall
   * write operations is not transacted, i.e. may partially fail. If any of the writing operations fails, the overall
   * operation is not continued, and the resulting state shall equal to the intersection of the original state and the
   * succeeded write operations.
   */
  @Override
  public void writeTimerSchedule(final TimerType timerType, final TimerSchedule timerSchedule)
      throws HeatPumpClientException {
    try {
      client.start().get();
      final SortedMap<Integer, Short> registerValueMap = RegisterUtils.buildRegisterValueMap(timerType, timerSchedule);
      RegisterUtils.extractContiguousRegisterValueRanges(registerValueMap)
          .forEach(c -> writeHoldingRegisterRange(c.registerRange(), c.values()));
    } catch (final ExecutionException | InterruptedException | ModbusException e) {
      LOG.error("Failed to write timer schedule", e);
      throw new HeatPumpClientException(e);
    } finally {
      client.stop();
    }
  }

  private short[] readHoldingRegisterRange(final RegisterRange registerRange) throws ModbusException {
    final RegistersModbusMessage request = RegistersModbusMessage.readHoldingsRequest(
        UNIT_ID, registerRange.startRegister(), registerRange.registerCount());
    return client.send(request).unwrap(RegistersModbusMessage.class).dataDecode();
  }

  private void writeHoldingRegisterRange(final RegisterRange registerRange, final short[] registerValues)
      throws ModbusException {
    final RegistersModbusMessage request = RegistersModbusMessage.writeHoldingsRequest(
        UNIT_ID, registerRange.startRegister(), registerValues);
    client.send(request);
  }
}
