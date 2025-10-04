package ufrn.imd.project.communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class TcpHttpServer {
  private final int port;

  public TcpHttpServer(int port) {
    this.port = port;
  }

  private void processRequest(Socket connection, RequestListener listener) {
    try {
      BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      String startLine = input.readLine();

      if (startLine == null) {
        return;
      }

      String[] startLineElements = startLine.split(" ");

      if (startLineElements.length < 3) {
        return;
      }

      String method = startLineElements[0];
      String path = startLineElements[1];
      String version = startLineElements[2];

      Map<String, String> headers = new HashMap<>();
      String nextLine;

      while ((nextLine = input.readLine()) != null && !nextLine.isEmpty()) {
        String[] headerElements = nextLine.split(": ");

        if (headerElements.length < 2) {
          continue;
        }

        headers.put(headerElements[0].toLowerCase().trim(), headerElements[1]);
      }

      int contentLength;

      try {
        contentLength = Integer.parseInt(headers.get("content-length"));
      } catch (NumberFormatException e) {
        contentLength = 0;
      }

      String body = null;

      if (contentLength > 0) {
        char[] buffer = new char[contentLength];

        input.read(buffer, 0, contentLength);

        body = String.copyValueOf(buffer);
      }

      listener.onRequest( new TcpHttpRequest(connection, method, path, version, headers, body));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void listen(RequestListener listener, ExecutorService executor) {
    try {
      ServerSocket serverSocket = new ServerSocket(port);

      while (true) {
        Socket connection = serverSocket.accept();

        executor.submit(() -> processRequest(connection, listener));
      }
    } catch (IOException e) {
      e.printStackTrace();

      throw new RuntimeException("Could not listen to tcp/http requests");
    }
  }

  public interface RequestListener {
    void onRequest(TcpHttpRequest request);
  }

  public class TcpHttpRequest {
    private final Socket connection;
    public final String method;
    public final String path;
    public final String version;
    private final Map<String, String> headers;
    private final String body;

    private TcpHttpRequest(
      Socket connection, String method, String path, String version, Map<String, String> headers, String body
    ) {
      this.connection = connection;
      this.method = method;
      this.path = path;
      this.version = version;
      this.headers = headers;
      this.body = body;
    }

    public String header(String key) {
      return headers.get(key.toLowerCase());
    }

    public <T> T body(Class<T> objectClass) {
      ObjectMapper mapper = new ObjectMapper();

      try {
        return mapper.readValue(body, objectClass);
      } catch (JsonProcessingException e) {
        return null;
      }
    }

    public void reply(Map<String, String> headers, Object data) {
      try {
        String response = "HTTP/1.1 200 OK\n";

        String body = null;

        if (data != null){
          ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

          try {
            body = writer.writeValueAsString(data);
          } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid json object to reply");
          }
        }

        if (body != null) {
          headers.put("Content-Length", Integer.toString(body.length()));
          headers.put("Content-Type", "application/json");
        }

        for (Map.Entry<String, String> entry : headers.entrySet()) {
          response += entry.getKey() + ": " + entry.getValue() + "\n";
        }

        response += "\n";

        if (body != null) {
          response += body;
        }

        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

        output.write(response, 0, response.length());
        output.flush();

        connection.close();
      } catch (IOException e) {
        e.printStackTrace();

        throw new RuntimeException("Could not send reply");
      }
    }

    public void reply(Object data) {
      reply(new HashMap<>(), data);
    }

    public void error(String message, boolean isClientError) {
      try {
        String response =
          "HTTP/1.1 " + (isClientError ? "400 Bad Request" : "500 Internal Server Error") + "\n"
          + "Content-Length: " + message.length() + "\n"
          + "Content-Type: text/plain\n"
          + "\n"
          + message;

        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

        output.write(response, 0, response.length());
        output.flush();

        connection.close();
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
