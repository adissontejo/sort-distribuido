package ufrn.imd.project.hearbeat.protocol;

import ufrn.imd.project.communication.TcpHttpClient;
import ufrn.imd.project.communication.TcpHttpServer;
import ufrn.imd.project.dtos.ComponentInstance;

public class HeartbeatTcpStrategy implements HeartbeatProtocolStrategy {
  @Override
  public void emit(String hostname, int port, ComponentInstance self) {
    TcpHttpClient client = new TcpHttpClient(hostname, port);

    client.send("POST", "/", self);
  }

  @Override
  public void listen(int port, ListenCallback callback) {
    TcpHttpServer server = new TcpHttpServer(port);

    server.listen((request) -> {
      callback.execute(request.body(ComponentInstance.class));

      request.reply(null);
    });
  }
}
