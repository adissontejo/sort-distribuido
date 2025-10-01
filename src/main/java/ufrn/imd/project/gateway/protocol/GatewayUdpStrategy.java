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
      try {
        if (request.method.equals("POST")) {
          if (request.path.equals("/quicksort")) {
            QuicksortRequest body = request.body(QuicksortRequest.class);

            if (body.data() == null) {
              request.error("data is required");
            } else {
              router.onQuicksortRequest(body, request::reply);
            }
          } else if (request.path.equals("/mergesort")) {
            MergesortRequest body = request.body(MergesortRequest.class);

            if (body.data() == null) {
              request.error("data is required");
            } else {
              router.onMergesortRequest(body, request::reply);
            }
          } else if (request.path.equals("/parallel-sort")) {
            ParallelSortRequest body = request.body(ParallelSortRequest.class);

            if (body.data() == null) {
              request.error("data is required");
            } else {
              router.onParallelSortRequest(request.body(ParallelSortRequest.class), request::reply);
            }
          }
        }
      } catch (Throwable e) {
        request.error("Internal server error");
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
