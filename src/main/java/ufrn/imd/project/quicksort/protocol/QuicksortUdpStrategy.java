package ufrn.imd.project.quicksort.protocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import ufrn.imd.project.dtos.QuicksortRequest;
import ufrn.imd.project.dtos.SortResponse;

public class QuicksortUdpStrategy implements QuicksortProtocolStrategy {
  @Override
  public void listen(int port, ListenCallback callback) throws IOException {
    DatagramSocket socket = new DatagramSocket(port);

    try {
      while (true) {
        byte[] receiveMessage = new byte[1024];

        DatagramPacket receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);

        socket.receive(receivePacket);

        String message = new String(receivePacket.getData()).trim();

        callback.execute(
          new QuicksortRequest(),
          new Reply() {
            public void send(SortResponse response) {
            };
          }
        );
      }
    } catch (Exception e) {
      socket.close();

      throw e;
    }
  }
}
