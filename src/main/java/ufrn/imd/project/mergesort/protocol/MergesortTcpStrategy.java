package ufrn.imd.project.mergesort.protocol;

import java.util.concurrent.ExecutorService;

import ufrn.imd.project.communication.TcpHttpServer;
import ufrn.imd.project.dtos.MergesortRequest;

public class MergesortTcpStrategy implements MergesortProtocolStrategy {
  @Override
  public void listen(int port, RequestListener listener, ExecutorService executor) {
    TcpHttpServer server = new TcpHttpServer(port);

    server.listen(
      (request) -> {
        if (request.method.equals("POST") && request.path.equals("/sort")) {
          listener.execute(request.body(MergesortRequest.class), request::reply);
        }
      },
      executor
    );
  }
}
