package ufrn.imd.project.gateway;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ufrn.imd.project.config.ServerThreadPool;
import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.dtos.MergesortRequest;
import ufrn.imd.project.dtos.ParallelSortCriteria;
import ufrn.imd.project.dtos.ParallelSortRequest;
import ufrn.imd.project.dtos.ParallelSortResponse;
import ufrn.imd.project.dtos.QuicksortRequest;
import ufrn.imd.project.dtos.SortResponse;
import ufrn.imd.project.gateway.QuorumCallback.QuorumConclusionListener;
import ufrn.imd.project.gateway.protocol.GatewayProtocolStrategy;
import ufrn.imd.project.gateway.protocol.GatewayProtocolStrategy.Reply;
import ufrn.imd.project.gateway.protocol.GatewayProtocolStrategy.Router;
import ufrn.imd.project.hearbeat.LoadBalancer;

public class GatewayController {
  private final GatewayProtocolStrategy protocolStrategy;
  private final LoadBalancer loadBalancer;

  public GatewayController(GatewayProtocolStrategy protocolStrategy, LoadBalancer loadBalancer) {
    this.protocolStrategy = protocolStrategy;
    this.loadBalancer = loadBalancer;
  }

  public void listen(int port) {
    System.out.println("Starting to listen to requests on port " + port + "...");

    try {
      protocolStrategy.listen(
        port,
        new Router() {
          @Override
          public void onQuicksortRequest(QuicksortRequest request, Reply<SortResponse> reply) {
            quicksort(request, reply);
          }

          @Override
          public void onMergesortRequest(MergesortRequest request, Reply<SortResponse> reply) {
            mergesort(request, reply);
          }

          @Override
          public void onParallelSortRequest(ParallelSortRequest request, Reply<ParallelSortResponse> reply) {
            parallelSort(request, reply);
          }
        },
        new ServerThreadPool(50, 300)
      );
    } catch (Exception e) {
      throw new RuntimeException("Error while trying to listen requests: " + e.getMessage());
    }
  }

  private void quicksort(QuicksortRequest request, Reply<SortResponse> reply) {
    try {
      if (request.data() == null) {
        reply.error("'data' is required", true);

        return;
      }

      ComponentInstance instance = loadBalancer.getInstanceFor("quicksort");

      if (instance == null) {
        reply.error("Quicksort unavailable at the moment", false);

        return;
      }

      SortResponse response = protocolStrategy.sendToQuicksort(instance, request);

      reply.send(response);
    } catch (Throwable e) {
      reply.error("Could not send request to quicksort", false);
    }
  }

  private void mergesort(MergesortRequest request, Reply<SortResponse> reply) {
    try {
      if (request.data() == null) {
        reply.error("'data' is required", true);

        return;
      }

      ComponentInstance instance = loadBalancer.getInstanceFor("mergesort");

      if (instance == null) {
        reply.error("Mergesort unavailable at the moment", false);

        return;
      }

      SortResponse response = protocolStrategy.sendToMergesort(instance, request);

      reply.send(response);
    } catch (Throwable e) {
      reply.error("Could not send request to mergesort", false);
    }
  }

  private void parallelSort(ParallelSortRequest request, Reply<ParallelSortResponse> reply) {
    if (request.data() == null) {
      reply.error("'data' is required", true);

      return;
    }

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    RequestWaitingList requestWaitingList = new RequestWaitingList();
    QuorumCallback quorumCallback = new QuorumCallback(
      request.criteria() == null ? ParallelSortCriteria.FIRST : request.criteria(),
      2,
      new QuorumConclusionListener() {
        @Override
        public void onConclusion(Map<String, SortResponse> responses) {
          reply.send(new ParallelSortResponse(responses.get("quicksort"), responses.get("mergesort")));

          executorService.shutdown();
          requestWaitingList.shutdown();;
        }

        @Override
        public void onError() {
          reply.error("Could not do parallel sort", false);

          executorService.shutdown();
          requestWaitingList.shutdown();;
        }
      }
    );

    executorService.submit(() -> {
      String componentKey = "quicksort";

      requestWaitingList.add(componentKey, quorumCallback);

      ComponentInstance instance = loadBalancer.getInstanceFor(componentKey);

      if (instance == null) {
        requestWaitingList.handleError(componentKey, new RuntimeException("Quicksort unavailable at the moment"));

        return;
      }

      try {
        SortResponse response = protocolStrategy.sendToQuicksort(
          instance,
          new QuicksortRequest(request.data())
        );

        requestWaitingList.handleResponse(componentKey, response);
      } catch (Throwable e) {
        requestWaitingList.handleError(componentKey, e);
      }
    });

    executorService.submit(() -> {
      String componentKey = "mergesort";

      requestWaitingList.add(componentKey, quorumCallback);

      ComponentInstance instance = loadBalancer.getInstanceFor(componentKey);

      if (instance == null) {
        requestWaitingList.handleError(componentKey, new RuntimeException("Mergesort unavailable at the moment"));

        return;
      }

      try {
        SortResponse response = protocolStrategy.sendToMergesort(
          instance,
          new MergesortRequest(request.data())
        );

        requestWaitingList.handleResponse(componentKey, response);
      } catch (Throwable e) {
        requestWaitingList.handleError(componentKey, e);
      }
    });
  }
}
