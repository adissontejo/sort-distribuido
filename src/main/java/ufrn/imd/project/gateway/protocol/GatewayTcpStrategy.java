package ufrn.imd.project.gateway.protocol;

import ufrn.imd.project.communication.TcpHttpClient;
import ufrn.imd.project.communication.TcpHttpServer;
import ufrn.imd.project.communication.TcpHttpClient.TcpHttpResponse;
import ufrn.imd.project.communication.TcpHttpServer.TcpHttpRequest;
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
          QuicksortRequest body = request.body(QuicksortRequest.class);

          if (body.data() == null) {
            request.error("data is required");
          } else {
            router.onQuicksortRequest(body, new TcpReply<>(request));
          }
        } else if (request.path.equals("/mergesort")) {
          MergesortRequest body = request.body(MergesortRequest.class);

          if (body.data() == null) {
            request.error("data is required");
          } else {
            router.onMergesortRequest(body, new TcpReply<>(request));
          }
        } else if (request.path.equals("/parallel-sort")) {
          ParallelSortRequest body = request.body(ParallelSortRequest.class);

          if (body.data() == null) {
            request.error("data is required");
          } else {
            router.onParallelSortRequest(request.body(ParallelSortRequest.class), new TcpReply<>(request));
          }
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

  private class TcpReply<T> implements Reply<T> {
    private TcpHttpRequest request;

    public TcpReply(TcpHttpRequest request) {
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
