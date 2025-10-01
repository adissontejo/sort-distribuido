package ufrn.imd.project.quicksort.protocol;

import ufrn.imd.project.dtos.QuicksortRequest;
import ufrn.imd.project.dtos.SortResponse;

public interface QuicksortProtocolStrategy {
  void listen(int port, RequestListener listener);

  public interface RequestListener {
    void execute(QuicksortRequest request, Reply reply);
  }

  public interface Reply {
    void send(SortResponse response);
  }
}
