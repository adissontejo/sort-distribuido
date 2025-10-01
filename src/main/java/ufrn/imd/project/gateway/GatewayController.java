package ufrn.imd.project.gateway;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
  private final ExecutorService executorService;

  public GatewayController(GatewayProtocolStrategy protocolStrategy, LoadBalancer loadBalancer) {
    this.protocolStrategy = protocolStrategy;
    this.loadBalancer = loadBalancer;
    this.executorService = new ThreadPoolExecutor(
      10,
      20,
      60,
      TimeUnit.SECONDS,
      new LinkedBlockingQueue<Runnable>(100),
      new ThreadPoolExecutor.AbortPolicy()
    );
  }

  public void listen(int port) {
    System.out.println("Starting to listen to requests on port " + port + "...");

    try {
      protocolStrategy.listen(port, new Router() {
        @Override
        public void onQuicksortRequest(QuicksortRequest request, Reply<SortResponse> reply) {
          executorService.submit(() -> quicksort(request, reply));
        }

        @Override
        public void onMergesortRequest(MergesortRequest request, Reply<SortResponse> reply) {
          executorService.submit(() -> mergesort(request, reply));
        }

        @Override
        public void onParallelSortRequest(ParallelSortRequest request, Reply<ParallelSortResponse> reply) {
          executorService.submit(() -> parallelSort(request, reply));
        }
      });
    } catch (Exception e) {
      throw new RuntimeException("Error while trying to listen requests: " + e.getMessage());
    }
  }

  private void quicksort(QuicksortRequest request, Reply<SortResponse> reply) {
    try {
      ComponentInstance instance = loadBalancer.getInstanceFor("quicksort");

      SortResponse response = protocolStrategy.sendToQuicksort(instance, request);

      reply.send(response);
    } catch (IOException e) {
      throw new RuntimeException("Could not send to quicksort");
    }
  }

  private void mergesort(MergesortRequest request, Reply<SortResponse> reply) {
    try {
      ComponentInstance instance = loadBalancer.getInstanceFor("mergesort");

      SortResponse response = protocolStrategy.sendToMergesort(instance, request);

      reply.send(response);
    } catch (IOException e) {
      throw new RuntimeException("Could not send to mergesort");
    }
  }

  private void parallelSort(ParallelSortRequest request, Reply<ParallelSortResponse> reply) {
    QuorumCallback quorumCallback = new QuorumCallback(
      request.criteria() == null ? ParallelSortCriteria.FIRST : request.criteria(),
      2,
      new QuorumConclusionListener() {
        @Override
        public void onConclusion(Map<String, SortResponse> responses) {
          reply.send(new ParallelSortResponse(responses.get("quicksort"), responses.get("mergesort")));
        }

        @Override
        public void onError() {
          // TODO Auto-generated method stub

        }
      }
    );

    RequestWaitingList requestWaitingList = new RequestWaitingList();

    ExecutorService executorService = Executors.newFixedThreadPool(2);

    executorService.submit(() -> {
      requestWaitingList.add("quicksort", quorumCallback);

      quicksort(new QuicksortRequest(request.data()), (response) -> {
        requestWaitingList.handleResponse("quicksort", response);
      });
    });

    executorService.submit(() -> {
      requestWaitingList.add("mergesort", quorumCallback);

      mergesort(new MergesortRequest(request.data()), (response) -> {
        requestWaitingList.handleResponse("mergesort", response);
      });
    });
  }
}
