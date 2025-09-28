package ufrn.imd.project.mergesort;

import java.util.List;

import ufrn.imd.project.dtos.SortResponse;
import ufrn.imd.project.mergesort.protocol.MergesortProtocolStrategy;

public class MergesortController {
  private final MergesortService service;
  private final MergesortProtocolStrategy protocolStrategy;

  public MergesortController(MergesortService service, MergesortProtocolStrategy protocolStrategy) {
    this.service = service;
    this.protocolStrategy = protocolStrategy;
  }

  public void listen(int port) {
    try {
      System.out.println("Starting to listen to requests on port " + port + "...");

      this.protocolStrategy.listen(port, (request, reply ) -> {
        System.out.println("New request received.");

        List<Integer> data = request.data();

        long initialTime = System.nanoTime();

        service.sort(data);

        long finalTime = System.nanoTime();

        reply.send(new SortResponse(data, finalTime - initialTime));
      });
    } catch (Exception e) {
      throw new RuntimeException("Error while trying to listen requests: " + e.getMessage());
    }
  }
}
