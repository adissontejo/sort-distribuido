package ufrn.imd.project.hearbeat.protocol;

import java.io.IOException;

import ufrn.imd.project.dtos.ComponentInstance;

public interface HeartbeatProtocolStrategy {
  public void emit(String hostname, int port, ComponentInstance self) throws IOException;
  public void listen(int port, ListenCallback callback) throws IOException, InterruptedException;

  interface ListenCallback {
    void execute(ComponentInstance instance);
  }
}
