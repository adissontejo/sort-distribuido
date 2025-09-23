package ufrn.imd.project.quicksort.protocol;

import java.io.IOException;

import ufrn.imd.project.dtos.QuicksortRequest;
import ufrn.imd.project.dtos.SortResponse;

public interface QuicksortProtocolStrategy {
  void listen(int port, ListenCallback callback) throws IOException, InterruptedException;

  public interface ListenCallback {
    void execute(QuicksortRequest request, Reply reply);
  }

  public interface Reply {
    void send(SortResponse response);
  }
}
