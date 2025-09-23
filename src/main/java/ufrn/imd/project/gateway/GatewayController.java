package ufrn.imd.project.gateway;

import java.util.ArrayList;
import java.util.List;

import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.dtos.QuicksortRequest;
import ufrn.imd.project.gateway.protocol.GatewayProtocolStrategy;
import ufrn.imd.project.hearbeat.LoadBalancer;

public class GatewayController {
  private final GatewayProtocolStrategy protocolStrategy;
  private final LoadBalancer loadBalancer;

  public GatewayController(GatewayProtocolStrategy protocolStrategy, LoadBalancer loadBalancer) {
    this.protocolStrategy = protocolStrategy;
    this.loadBalancer = loadBalancer;
  }

  public void listen(int port) {
    System.out.println("Starting to listen to requests on port " + port + "...");

    try {
      this.protocolStrategy.listen(port, () -> {
        System.out.println("Received new quicksort request.");

        ComponentInstance instance = loadBalancer.getInstanceFor("quicksort");

        if (instance == null) {
          return;
        }

        List<Integer> data = new ArrayList<>();

        data.add(5);

        protocolStrategy.sendToQuicksort(instance, new QuicksortRequest(data));
      });
    } catch (Exception e) {
      throw new RuntimeException("Error while trying to listen requests: " + e.getMessage());
    }
  }
}
