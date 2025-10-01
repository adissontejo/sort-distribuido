package ufrn.imd.project.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class UdpClient {
  private final String hostname;
  private final int port;
  private DatagramSocket socket;

  public UdpClient(String hostname, int port) {
    this.hostname = hostname;
    this.port = port;
  }

  public void send(String method, String path, Object data) {
    String message = method + " " + path + "\n";

    if (data != null) {
      ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

      try {
        message += writer.writeValueAsString(data);
      } catch (JsonProcessingException e) {}
    }

    try {
      if (socket == null) {
        socket = new DatagramSocket();
      }

      DatagramPacket sendPacket = new DatagramPacket(
        message.getBytes(), message.length(), InetAddress.getByName(hostname), port
      );

      socket.send(sendPacket);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public UdpResponse receive() {
    try {
      byte[] receiveMessage = new byte[1024 * 1024];

      DatagramPacket receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);

      if (socket == null) {
        socket = new DatagramSocket();
      }

      socket.receive(receivePacket);

      socket.close();

      String message = new String(receivePacket.getData()).trim();

      String[] lines = message.split("\r?\n");

      String startLine = lines[0];

      String[] startLineElements = startLine.split(" ");

      int statusCode;

      try {
        statusCode = Integer.parseInt(startLineElements[0]);
      } catch (NumberFormatException e) {
        return null;
      }

      String body = null;

      if (lines.length > 1) {
        body = "";

        for (int i = 1; i < lines.length; i++) {
          body += lines[i] + "\n";
        }
      }

      return new UdpResponse(statusCode, body);
    } catch (IOException e) {
      e.printStackTrace();

      return null;
    }
  }

  public void close() {
    if (socket != null) {
      socket.close();
    }
  }

  public class UdpResponse {
    public final int statusCode;
    private final String body;

    private UdpResponse(int statusCode, String body) {
      this.statusCode = statusCode;
      this.body = body;
    }

    public <T> T body(Class<T> objectClass) {
      ObjectMapper mapper = new ObjectMapper();

      try {
        return mapper.readValue(body, objectClass);
      } catch (JsonProcessingException e) {
        return null;
      }
    }
  }
}
