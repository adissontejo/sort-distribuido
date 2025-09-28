package ufrn.imd.project.quicksort.protocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import ufrn.imd.project.dtos.QuicksortRequest;

public class QuicksortUdpStrategy implements QuicksortProtocolStrategy {
  @Override
  public void listen(int port, RequestListener listener) throws IOException {
    DatagramSocket socket = new DatagramSocket(port);

    try {
      while (true) {
        byte[] receiveMessage = new byte[1024];

        DatagramPacket receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);

        socket.receive(receivePacket);

        String message = new String(receivePacket.getData()).trim();

        listener.execute(
          new QuicksortRequest(new ArrayList<>()),
          response -> {

          }
        );
      }
    } catch (Exception e) {
      socket.close();

      throw e;
    }
  }
}
