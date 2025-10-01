package ufrn.imd.project.quicksort.protocol;

import java.io.IOException;

import ufrn.imd.project.communication.UdpServer;
import ufrn.imd.project.dtos.QuicksortRequest;

public class QuicksortUdpStrategy implements QuicksortProtocolStrategy {
  @Override
  public void listen(int port, RequestListener listener) throws IOException, InterruptedException {
    UdpServer server = new UdpServer(port);

    server.listen((request) -> {
      listener.execute(request.body(QuicksortRequest.class), request::reply);
    });
  }
}
