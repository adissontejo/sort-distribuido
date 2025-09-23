package ufrn.imd.project.quicksort;

import ufrn.imd.project.quicksort.protocol.QuicksortProtocolStrategy;

public class QuicksortController {
  private final QuicksortProtocolStrategy protocolStrategy;

  public QuicksortController(QuicksortProtocolStrategy protocolStrategy) {
    this.protocolStrategy = protocolStrategy;
  }

  public void listen(int port) {
    try {
      System.out.println("Starting to listen to requests on port " + port + "...");

      this.protocolStrategy.listen(port, (request, reply ) -> {
        System.out.println("New request received.");
      });
    } catch (Exception e) {
      throw new RuntimeException("Error while trying to listen requests: " + e.getMessage());
    }
  }
}
