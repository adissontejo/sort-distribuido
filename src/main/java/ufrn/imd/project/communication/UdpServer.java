package ufrn.imd.project.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import ufrn.imd.project.config.ServerThreadPool;

public class UdpServer {
  private final int port;

  public UdpServer(int port) {
    this.port = port;
  }

  private void processRequest(DatagramSocket socket, DatagramPacket receivePacket, RequestListener listener) {
    String message = new String(receivePacket.getData()).trim();

    String[] lines = message.split("\r?\n");

    String startLine = lines[0];

    String[] startLineElements = startLine.split(" +");

    if (startLineElements.length < 2) {
      return;
    }

    String method = startLineElements[0];
    String path = startLineElements[1];

    String body = null;

    if (lines.length > 1) {
      body = "";

      for (int i = 1; i < lines.length; i++) {
        body += lines[i] + "\n";
      }
    }

    listener.onRequest(
      new UdpRequest(socket, receivePacket.getAddress(), receivePacket.getPort(), method, path, body)
    );
  }

  public void listen(RequestListener listener) {
    try {
      DatagramSocket socket = new DatagramSocket(port);
      ServerThreadPool pool = new ServerThreadPool();

      while (true) {
        try {
          byte[] receiveMessage = new byte[1024 * 1024];

          DatagramPacket receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);

          socket.receive(receivePacket);

          pool.submit(() -> processRequest(socket, receivePacket, listener));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();

      throw new RuntimeException("Could not listen to udp requests");
    }
  }

  public interface RequestListener {
    void onRequest(UdpRequest request);
  }

  public class UdpRequest {
    private final DatagramSocket socket;
    private final InetAddress address;
    private final int port;
    public final String method;
    public final String path;
    private final String body;

    private UdpRequest(DatagramSocket socket, InetAddress address, int port, String method, String path, String body) {
      this.socket = socket;
      this.address = address;
      this.port = port;
      this.method = method;
      this.path = path;
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

    public void reply(Object data) {
      String message = "200 OK\n";

      if (data != null) {
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

        try {
          message += writer.writeValueAsString(data);
        } catch (JsonProcessingException e) {
          throw new RuntimeException("Invalid json object to reply");
        }
      }

      DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.length(), address, port);

      try {
        socket.send(sendPacket);
      } catch (IOException e) {
        e.printStackTrace();

        throw new RuntimeException("Could not send reply");
      }
    }

    public void error(String message, boolean isClientError) {
      String response =
        (isClientError ? "400 Bad Request" : "500 Internal Server Error") + "\n"
        + message;

      DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.length(), address, port);

      try {
        socket.send(sendPacket);
      } catch (IOException e) {
        e.printStackTrace();

        throw new RuntimeException("Could not send reply");
      }
    }

    public void error(String message) {
      error(message, true);
    }
  }
}
