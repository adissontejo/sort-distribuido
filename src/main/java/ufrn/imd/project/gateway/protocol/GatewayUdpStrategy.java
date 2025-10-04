package ufrn.imd.project.gateway.protocol;

import java.util.concurrent.ExecutorService;

import ufrn.imd.project.communication.UdpClient;
import ufrn.imd.project.communication.UdpClient.UdpResponse;
import ufrn.imd.project.communication.UdpServer.UdpRequest;
import ufrn.imd.project.communication.UdpServer;
import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.dtos.MergesortRequest;
import ufrn.imd.project.dtos.ParallelSortRequest;
import ufrn.imd.project.dtos.QuicksortRequest;
import ufrn.imd.project.dtos.SortResponse;

public class GatewayUdpStrategy implements GatewayProtocolStrategy {
  @Override
  public void listen(int port, Router router, ExecutorService executor) {
    UdpServer server = new UdpServer(port);

    server.listen(
      (request) -> {
        try {
          if (request.method.equals("POST")) {
            if (request.path.equals("/quicksort")) {
              QuicksortRequest body = request.body(QuicksortRequest.class);

              if (body.data() == null) {
                request.error("data is required");
              } else {
                router.onQuicksortRequest(body, new UdpReply<>(request));
              }
            } else if (request.path.equals("/mergesort")) {
              MergesortRequest body = request.body(MergesortRequest.class);

              if (body.data() == null) {
                request.error("data is required");
              } else {
                router.onMergesortRequest(body, new UdpReply<>(request));
              }
            } else if (request.path.equals("/parallel-sort")) {
              ParallelSortRequest body = request.body(ParallelSortRequest.class);

              if (body.data() == null) {
                request.error("data is required");
              } else {
                router.onParallelSortRequest(request.body(ParallelSortRequest.class), new UdpReply<>(request));
              }
            }
          }
        } catch (Throwable e) {
          request.error("Internal server error", false);
        }
      },
      executor
    );
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

  private class UdpReply<T> implements Reply<T> {
    private UdpRequest request;

    public UdpReply(UdpRequest request) {
      this.request = request;
    }

    @Override
    public void send(T response) {
      request.reply(response);
    }

    @Override
    public void error(String message, boolean isValidationError) {
      request.error(message, isValidationError);
    }
  }
}
