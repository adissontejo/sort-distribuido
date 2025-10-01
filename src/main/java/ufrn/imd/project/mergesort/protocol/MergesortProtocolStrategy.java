package ufrn.imd.project.mergesort.protocol;

import ufrn.imd.project.dtos.MergesortRequest;
import ufrn.imd.project.dtos.SortResponse;

public interface MergesortProtocolStrategy {
  void listen(int port, RequestListener listener);

  public interface RequestListener {
    void execute(MergesortRequest request, Reply reply);
  }

  public interface Reply {
    void send(SortResponse response);
  }
}
