package ufrn.imd.project.quicksort.protocol;

import java.util.concurrent.ExecutorService;

import ufrn.imd.project.communication.TcpHttpServer;
import ufrn.imd.project.dtos.QuicksortRequest;

public class QuicksortTcpStrategy implements QuicksortProtocolStrategy {
  @Override
  public void listen(int port, RequestListener listener, ExecutorService executor) {
    TcpHttpServer server = new TcpHttpServer(port);

    server.listen(
      (request) -> {
        if (request.method.equals("POST") && request.path.equals("/sort")) {
          listener.execute(request.body(QuicksortRequest.class), request::reply);
        }
      },
      executor
    );
  }
}
