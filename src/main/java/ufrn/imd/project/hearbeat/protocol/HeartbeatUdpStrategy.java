package ufrn.imd.project.hearbeat.protocol;

import java.io.IOException;
import java.util.concurrent.Executors;

import ufrn.imd.project.communication.UdpClient;
import ufrn.imd.project.communication.UdpServer;
import ufrn.imd.project.dtos.ComponentInstance;

public class HeartbeatUdpStrategy implements HeartbeatProtocolStrategy {
  @Override
  public void emit(String gatewayHostname, int gatewayPort, ComponentInstance self) throws IOException {
    UdpClient client = new UdpClient(gatewayHostname, gatewayPort);

    client.send("POST", "/", self);
  }

  @Override
  public void listen(int port, ListenCallback callback) throws IOException {
    UdpServer server = new UdpServer(port);

    server.listen(
      (request) -> {
        callback.execute(request.body(ComponentInstance.class));
      },
      Executors.newSingleThreadExecutor()
    );
  }
}
