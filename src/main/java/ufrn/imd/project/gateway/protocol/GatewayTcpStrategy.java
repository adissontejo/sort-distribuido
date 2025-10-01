package ufrn.imd.project.gateway.protocol;

import ufrn.imd.project.communication.TcpHttpClient;
import ufrn.imd.project.communication.TcpHttpServer;
import ufrn.imd.project.communication.TcpHttpClient.TcpHttpResponse;
import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.dtos.MergesortRequest;
import ufrn.imd.project.dtos.ParallelSortRequest;
import ufrn.imd.project.dtos.QuicksortRequest;
import ufrn.imd.project.dtos.SortResponse;

public class GatewayTcpStrategy implements GatewayProtocolStrategy {
  @Override
  public void listen(int port, Router router) {
    TcpHttpServer server = new TcpHttpServer(port);

    server.listen((request) -> {
      if (request.method.equals("POST")) {
        if (request.path.equals("/quicksort")) {
          router.onQuicksortRequest(request.body(QuicksortRequest.class), request::reply);
        } else if (request.path.equals("/mergesort")) {
          router.onMergesortRequest(request.body(MergesortRequest.class), request::reply);
        } else if (request.path.equals("/parallel-sort")) {
          router.onParallelSortRequest(request.body(ParallelSortRequest.class), request::reply);
        }
      }
    });
  }

  @Override
  public SortResponse sendToQuicksort(ComponentInstance instance, QuicksortRequest request) {
    TcpHttpClient client = new TcpHttpClient(instance.hostname(), instance.port());

    TcpHttpResponse response = client.send("POST", "/sort", request);

    return response.body(SortResponse.class);
  }

  @Override
  public SortResponse sendToMergesort(ComponentInstance instance, MergesortRequest request) {
    TcpHttpClient client = new TcpHttpClient(instance.hostname(), instance.port());

    TcpHttpResponse response = client.send("POST", "/sort", request);

    return response.body(SortResponse.class);
  }
}
