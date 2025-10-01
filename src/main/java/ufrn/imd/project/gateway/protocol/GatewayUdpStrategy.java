package ufrn.imd.project.gateway.protocol;

import ufrn.imd.project.communication.UdpClient;
import ufrn.imd.project.communication.UdpClient.UdpResponse;
import ufrn.imd.project.communication.UdpServer;
import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.dtos.MergesortRequest;
import ufrn.imd.project.dtos.ParallelSortRequest;
import ufrn.imd.project.dtos.QuicksortRequest;
import ufrn.imd.project.dtos.SortResponse;

public class GatewayUdpStrategy implements GatewayProtocolStrategy {
  @Override
  public void listen(int port, Router router) {
    UdpServer server = new UdpServer(port);

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
    UdpClient client = new UdpClient(instance.hostname(), instance.port());

    client.send("POST", "/sort", request);

    UdpResponse response = client.receive();

    return response.body(SortResponse.class);
  }

  @Override
  public SortResponse sendToMergesort(ComponentInstance instance, MergesortRequest request) {
    UdpClient client = new UdpClient(instance.hostname(), instance.port());

    client.send("POST", "/sort", request);

    UdpResponse response = client.receive();

    return response.body(SortResponse.class);
  }
}
