package ufrn.imd.project.gateway.protocol;

import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.dtos.MergesortRequest;
import ufrn.imd.project.dtos.ParallelSortRequest;
import ufrn.imd.project.dtos.ParallelSortResponse;
import ufrn.imd.project.dtos.QuicksortRequest;
import ufrn.imd.project.dtos.SortResponse;

public interface GatewayProtocolStrategy {
  public void listen(int port, Router router);
  public SortResponse sendToQuicksort(ComponentInstance instance, QuicksortRequest request);
  public SortResponse sendToMergesort(ComponentInstance instance, MergesortRequest request);

  public interface Router {
    void onQuicksortRequest(QuicksortRequest request, Reply<SortResponse> reply);
    void onMergesortRequest(MergesortRequest request, Reply<SortResponse> reply);
    void onParallelSortRequest(ParallelSortRequest request, Reply<ParallelSortResponse> reply);
  }

  public interface Reply<T> {
    public void send(T response);
  }
}
