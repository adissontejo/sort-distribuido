package ufrn.imd.project.quicksort;

import ufrn.imd.project.config.ConfigLoader;
import ufrn.imd.project.config.PortManager;
import ufrn.imd.project.config.Protocol;
import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.hearbeat.HeartbeatEmitter;
import ufrn.imd.project.hearbeat.protocol.HeartbeatGrpcStrategy;
import ufrn.imd.project.hearbeat.protocol.HeartbeatProtocolStrategy;
import ufrn.imd.project.hearbeat.protocol.HeartbeatTcpStrategy;
import ufrn.imd.project.hearbeat.protocol.HeartbeatUdpStrategy;
import ufrn.imd.project.quicksort.protocol.QuicksortGrpcStrategy;
import ufrn.imd.project.quicksort.protocol.QuicksortProtocolStrategy;
import ufrn.imd.project.quicksort.protocol.QuicksortTcpStrategy;
import ufrn.imd.project.quicksort.protocol.QuicksortUdpStrategy;

public class QuicksortServer {
  public static void main(String[] args) {
    Protocol protocol = ConfigLoader.getProtocol();

    HeartbeatProtocolStrategy heartbeatProtocolStrategy;
    QuicksortProtocolStrategy protocolStrategy;

    if (protocol == Protocol.UDP) {
      heartbeatProtocolStrategy = new HeartbeatUdpStrategy();
      protocolStrategy = new QuicksortUdpStrategy();
    } else if (protocol == Protocol.TCP) {
      heartbeatProtocolStrategy = new HeartbeatTcpStrategy();
      protocolStrategy = new QuicksortTcpStrategy();
    } else {
      heartbeatProtocolStrategy = new HeartbeatGrpcStrategy();
      protocolStrategy = new QuicksortGrpcStrategy();
    }

    int port = PortManager.allocatePort();

    ComponentInstance self = new ComponentInstance("quicksort", "localhost", port);
    HeartbeatEmitter heartbeatEmitter = new HeartbeatEmitter(heartbeatProtocolStrategy, self);
    QuicksortService service = new QuicksortService();
    QuicksortController controller = new QuicksortController(service, protocolStrategy);

    heartbeatEmitter.start();
    controller.listen(port);
  }
}
