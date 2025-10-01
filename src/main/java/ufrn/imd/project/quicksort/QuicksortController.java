package ufrn.imd.project.quicksort;

import java.util.List;

import ufrn.imd.project.dtos.SortResponse;
import ufrn.imd.project.quicksort.protocol.QuicksortProtocolStrategy;

public class QuicksortController {
  private final QuicksortService service;
  private final QuicksortProtocolStrategy protocolStrategy;

  public QuicksortController(QuicksortService service, QuicksortProtocolStrategy protocolStrategy) {
    this.service = service;
    this.protocolStrategy = protocolStrategy;
  }

  public void listen(int port) {
    System.out.println("Starting to listen to requests on port " + port + "...");

    this.protocolStrategy.listen(port, (request, reply ) -> {
      System.out.println("New request received.");

      List<Integer> data = request.data();

      long initialTime = System.nanoTime();

      service.sort(data);

      long finalTime = System.nanoTime();

      reply.send(new SortResponse(data, finalTime - initialTime));
    });
  }
}
