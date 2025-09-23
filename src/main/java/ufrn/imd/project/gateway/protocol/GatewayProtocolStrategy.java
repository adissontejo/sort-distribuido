package ufrn.imd.project.gateway.protocol;

import java.io.IOException;

import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.dtos.QuicksortRequest;

public interface GatewayProtocolStrategy {
  public void listen(int port, ListenCallback callback) throws IOException, InterruptedException;
  public void sendToQuicksort(ComponentInstance instance, QuicksortRequest request);

  public interface ListenCallback {
    void execute();
  }
}
