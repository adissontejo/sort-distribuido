package ufrn.imd.project.hearbeat.protocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import ufrn.imd.project.dtos.ComponentInstance;

public class HeartbeatUdpStrategy implements HeartbeatProtocolStrategy {
  @Override
  public void emit(String gatewayHostname, int gatewayPort, ComponentInstance self) throws IOException {
    DatagramSocket socket = new DatagramSocket();

    String message = self.componentKey() + " " + self.hostname() + " " + self.port();

    DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName(gatewayHostname), gatewayPort);

    socket.send(sendPacket);

    socket.close();
  }

  @Override
  public void listen(int port, ListenCallback callback) throws IOException {
    DatagramSocket socket = new DatagramSocket(port);

    try {
      while (true) {
        byte[] receiveMessage = new byte[1024];

        DatagramPacket receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);

        socket.receive(receivePacket);

        String message = new String(receivePacket.getData()).trim();

        String[] words = message.split(" ");

        if (words.length < 3) {
          continue;
        }

        try {
          callback.execute(
            new ComponentInstance(
              words[0],
              words[1],
              Integer.parseInt(words[2])
            )
          );
        } catch (NumberFormatException e) {
          continue;
        }
      }
    } catch (IOException e) {
      socket.close();

      throw e;
    }
  }
}
