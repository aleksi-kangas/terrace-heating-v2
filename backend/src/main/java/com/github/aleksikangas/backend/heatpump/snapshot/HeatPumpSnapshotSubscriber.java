/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.heatpump.snapshot;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.net.URI;
import java.util.UUID;
import org.eclipse.paho.mqttv5.client.IMqttClient;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Subscribes to <code>VMi 9</code> topic on the MQTT broker. Publishes {@link HeatPumpSnapshotEvent}s upon subscription
 * data.
 */
@Service
public final class HeatPumpSnapshotSubscriber implements MqttCallback {

  private static final Logger LOG = LoggerFactory.getLogger(HeatPumpSnapshotSubscriber.class);

  private static final UUID CLIENT_ID = UUID.randomUUID();
  private static final MqttConnectionOptions CONNECTION_OPTIONS = new MqttConnectionOptions();
  private static final int QOS_0 = 0;
  private static final URI SERVER_URI = URI.create(System.getenv("MQTT_BROKER_URI"));
  private static final String VMI_9_TOPIC = "VMi 9";

  private final IMqttClient mqttClient = new MqttClient(
      SERVER_URI.toString(),
      CLIENT_ID.toString(),
      new MemoryPersistence());
  private final ApplicationEventPublisher applicationEventPublisher;

  public HeatPumpSnapshotSubscriber(@Autowired final ApplicationEventPublisher applicationEventPublisher)
      throws MqttException {
    this.applicationEventPublisher = applicationEventPublisher;
    mqttClient.setCallback(this);
  }

  @PostConstruct
  public void postConstruct() throws MqttException {
    mqttClient.connect(CONNECTION_OPTIONS);
    mqttClient.subscribe(VMI_9_TOPIC, QOS_0);
  }

  @PreDestroy
  public void preDestroy() throws MqttException {
    mqttClient.disconnect();
  }

  @Override
  public void disconnected(final MqttDisconnectResponse disconnectResponse) {
    LOG.info("MQTT client disconnected: DisconnectResponse={}", disconnectResponse);
  }

  @Override
  public void mqttErrorOccurred(final MqttException exception) {
    LOG.error("MQTT exception occurred", exception);
  }

  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {
    LOG.debug("MQTT message arrived: Topic={}, Message={}", topic, message);
    if (VMI_9_TOPIC.equals(topic)) {
      // TODO
    }
  }

  @Override
  public void deliveryComplete(final IMqttToken token) {
    LOG.debug("MQTT delivery complete: Token={}", token);
  }

  @Override
  public void connectComplete(final boolean reconnect, final String serverURI) {
    LOG.info("MQTT client connected: {}", serverURI);
  }

  @Override
  public void authPacketArrived(final int reasonCode, final MqttProperties properties) {
    LOG.debug("MQTT AUTH packet arrived: ReasonCode={}, Properties={}", reasonCode, properties);
  }
}
