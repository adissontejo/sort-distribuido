package ufrn.imd.project.mergesort.protocol;

import ufrn.imd.project.communication.UdpServer;
import ufrn.imd.project.dtos.MergesortRequest;

public class MergesortUdpStrategy implements MergesortProtocolStrategy {
  @Override
  public void listen(int port, RequestListener listener) {
    UdpServer server = new UdpServer(port);

    server.listen((request) -> {
      listener.execute(request.body(MergesortRequest.class), request::reply);
    });
  }
}
