package ufrn.imd.project.hearbeat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ufrn.imd.project.config.PortManager;
import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.hearbeat.protocol.HeartbeatProtocolStrategy;

public class HeartbeatEmitter {
  private final HeartbeatProtocolStrategy protocolStrategy;
  private final ComponentInstance self;

  public HeartbeatEmitter(HeartbeatProtocolStrategy protocolStrategy, ComponentInstance self) {
    this.protocolStrategy = protocolStrategy;
    this.self = self;
  }

  public void start() {
    System.out.println("Starting to emit heartbeats to gateway on localhost:" + PortManager.heartbeatListenerPort + "...");

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    scheduler.scheduleAtFixedRate(this::emit, 0, 1, TimeUnit.SECONDS);
  }

  private void emit() {
    try {
      this.protocolStrategy.emit("localhost", PortManager.heartbeatListenerPort, self);
    } catch (Throwable e) {
      System.out.println("Could not send heartbeat message to gateway.");
    }
  }
}
