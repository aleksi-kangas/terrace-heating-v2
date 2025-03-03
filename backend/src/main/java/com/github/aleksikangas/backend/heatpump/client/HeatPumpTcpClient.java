/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.client;

import com.github.aleksikangas.backend.domain.snapshot.TemperatureSnapshot;
import com.github.aleksikangas.backend.heatpump.client.parsers.TemperatureSnapshotParser;
import com.github.aleksikangas.backend.heatpump.client.registers.RegisterRange;
import com.github.aleksikangas.backend.heatpump.client.registers.TemperatureRegisters;
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

  private short[] readHoldingRegisterRange(final RegisterRange registerRange) {
    final RegistersModbusMessage request = RegistersModbusMessage.readHoldingsRequest(
        UNIT_ID, registerRange.startRegister(), registerRange.registerCount());
    return client.send(request).unwrap(RegistersModbusMessage.class).dataDecode();
  }
}
