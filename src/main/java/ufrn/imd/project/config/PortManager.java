package ufrn.imd.project.config;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class PortManager {
  public static final int gatewayPort = 3000;
  public static final int heartbeatListenerPort = 3001;

  private static boolean isPortAvailable(int port) {
    Protocol protocol = ConfigLoader.getProtocol();

    try {
      if (protocol == Protocol.UDP) {
        DatagramSocket socket = new DatagramSocket(port);

        socket.close();
      } else {
        ServerSocket socket = new ServerSocket(port);

        socket.close();
      }

      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public static int allocatePort() {
    int port = 3002;

    while (!isPortAvailable(port)) {
      port += 1;
    }

    return port;
  }
}
