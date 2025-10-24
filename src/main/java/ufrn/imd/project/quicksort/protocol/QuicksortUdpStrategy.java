package ufrn.imd.project.quicksort.protocol;

import ufrn.imd.project.communication.UdpServer;
import ufrn.imd.project.dtos.QuicksortRequest;

public class QuicksortUdpStrategy implements QuicksortProtocolStrategy {
  @Override
  public void listen(int port, RequestListener listener) {
    UdpServer server = new UdpServer(port);

    server.listen((request) -> {
      if (request.method.equals("POST") && request.path.equals("/sort")) {
        listener.execute(request.body(QuicksortRequest.class), request::reply);
      }
    });
  }
}
