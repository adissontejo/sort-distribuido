package ufrn.imd.project.gateway;

import javax.sound.sampled.Port;

import ufrn.imd.project.config.ConfigLoader;
import ufrn.imd.project.config.PortManager;
import ufrn.imd.project.config.Protocol;
import ufrn.imd.project.gateway.protocol.GatewayGrpcStrategy;
import ufrn.imd.project.gateway.protocol.GatewayProtocolStrategy;
import ufrn.imd.project.hearbeat.HeartbeatListener;
import ufrn.imd.project.hearbeat.LoadBalancer;
import ufrn.imd.project.hearbeat.protocol.HeartbeatGrpcStrategy;
import ufrn.imd.project.hearbeat.protocol.HeartbeatProtocolStrategy;
import ufrn.imd.project.hearbeat.protocol.HeartbeatTcpStrategy;
import ufrn.imd.project.hearbeat.protocol.HeartbeatUdpStrategy;

public class Gateway {
  public static void main(String[] args) {
    Protocol protocol = ConfigLoader.getProtocol();

    HeartbeatProtocolStrategy heartbeatProtocolStrategy;
    GatewayProtocolStrategy protocolStrategy = new GatewayGrpcStrategy();

    if (protocol == Protocol.UDP) {
      heartbeatProtocolStrategy = new HeartbeatUdpStrategy();
    } else if (protocol == Protocol.TCP) {
      heartbeatProtocolStrategy = new HeartbeatTcpStrategy();
    } else {
      heartbeatProtocolStrategy = new HeartbeatGrpcStrategy();
    }

    LoadBalancer loadBalancer = new LoadBalancer();
    HeartbeatListener heartbeatListener = new HeartbeatListener(heartbeatProtocolStrategy, loadBalancer);
    GatewayController controller = new GatewayController(protocolStrategy, loadBalancer);

    heartbeatListener.start();
    controller.listen(PortManager.gatewayPort);
  }
}
