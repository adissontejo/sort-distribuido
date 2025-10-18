package ufrn.imd.project.mergesort.protocol;

import java.util.concurrent.ExecutorService;

import ufrn.imd.project.dtos.MergesortRequest;
import ufrn.imd.project.dtos.SortResponse;

public interface MergesortProtocolStrategy {
  void listen(int port, RequestListener listener, ExecutorService executor);

  public static interface RequestListener {
    void execute(MergesortRequest request, Reply reply);
  }

  public static interface Reply {
    void send(SortResponse response);
  }
}
