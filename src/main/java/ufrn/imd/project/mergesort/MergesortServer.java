package ufrn.imd.project.mergesort;

import ufrn.imd.project.config.ConfigLoader;
import ufrn.imd.project.config.PortManager;
import ufrn.imd.project.config.Protocol;
import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.hearbeat.HeartbeatEmitter;
import ufrn.imd.project.hearbeat.protocol.HeartbeatGrpcStrategy;
import ufrn.imd.project.hearbeat.protocol.HeartbeatProtocolStrategy;
import ufrn.imd.project.hearbeat.protocol.HeartbeatTcpStrategy;
import ufrn.imd.project.hearbeat.protocol.HeartbeatUdpStrategy;
import ufrn.imd.project.mergesort.protocol.MergesortGrpcStrategy;
import ufrn.imd.project.mergesort.protocol.MergesortProtocolStrategy;

public class MergesortServer {
  public static void main(String[] args) {
    Protocol protocol = ConfigLoader.getProtocol();

    HeartbeatProtocolStrategy heartbeatProtocolStrategy;
    MergesortProtocolStrategy protocolStrategy;

    if (protocol == Protocol.UDP) {
      heartbeatProtocolStrategy = new HeartbeatUdpStrategy();
      protocolStrategy = new MergesortGrpcStrategy();
    } else if (protocol == Protocol.TCP) {
      heartbeatProtocolStrategy = new HeartbeatTcpStrategy();
      protocolStrategy = new MergesortGrpcStrategy();
    } else {
      heartbeatProtocolStrategy = new HeartbeatGrpcStrategy();
      protocolStrategy = new MergesortGrpcStrategy();
    }

    int port = PortManager.allocatePort();

    ComponentInstance self = new ComponentInstance("mergesort", "localhost", port);
    HeartbeatEmitter heartbeatEmitter = new HeartbeatEmitter(heartbeatProtocolStrategy, self);
    MergesortService service = new MergesortService();
    MergesortController controller = new MergesortController(service, protocolStrategy);

    heartbeatEmitter.start();
    controller.listen(port);
  }
}
