package ufrn.imd.project.hearbeat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ufrn.imd.project.config.PortManager;
import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.hearbeat.protocol.HeartbeatProtocolStrategy;

public class HeartbeatListener extends Thread {
  private final HeartbeatProtocolStrategy protocolStrategy;
  private final LoadBalancer loadBalancer;
  private final Map<ComponentInstance, LocalDateTime> lastMessageTimes;

  public HeartbeatListener(HeartbeatProtocolStrategy protocolStrategy, LoadBalancer loadBalancer) {
    this.protocolStrategy = protocolStrategy;
    this.loadBalancer = loadBalancer;
    this.lastMessageTimes = new ConcurrentHashMap<>();
  }

  @Override
  public void run() {
    try {
      ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

      scheduler.scheduleAtFixedRate(this::checkConnections, 2, 1, TimeUnit.SECONDS);

      System.out.println("Listening for heartbeat signals on port " +  PortManager.heartbeatListenerPort +  "...");

      this.protocolStrategy.listen(PortManager.heartbeatListenerPort, (ComponentInstance instance) -> {
        this.addConnection(instance);
      });
    } catch (Exception e) {
      System.out.println("Error trying to listen hearbeats: " + e.getMessage());
    }
  }

  private synchronized void addConnection(ComponentInstance instance) {
    if (!lastMessageTimes.containsKey(instance)) {
      System.out.println(
        "New " + instance.componentKey() + " instance found: " + instance.hostname() + ":" + instance.port()
      );

      loadBalancer.addInstance(instance);
    }

    lastMessageTimes.put(instance, LocalDateTime.now());
  }

  private synchronized void checkConnections() {
    LocalDateTime now = LocalDateTime.now();

    Iterator<Map.Entry<ComponentInstance, LocalDateTime>> iterator = lastMessageTimes.entrySet().iterator();

    while (iterator.hasNext()) {
      Map.Entry<ComponentInstance, LocalDateTime> entry = iterator.next();
      ComponentInstance instance = entry.getKey();
      LocalDateTime lastMessageTime = entry.getValue();

      long secondsDiff = Duration.between(lastMessageTime, now).toSeconds();

      if (secondsDiff > 2) {
        System.out.println(
          "Lost " + instance.componentKey() + " instance on " + instance.hostname() + ":" + instance.port()
        );

        loadBalancer.removeInstance(instance);

        iterator.remove();
      }
    }
  }
}
