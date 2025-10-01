package ufrn.imd.project.communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class TcpHttpClient {
  private final String hostname;
  private final int port;

  public TcpHttpClient(String hostname, int port) {
    this.hostname = hostname;
    this.port = port;
  }

  public TcpHttpResponse send(String method, String path, Map<String, String> headers, Object data) {
    try {
      Socket connection = new Socket(InetAddress.getByName(hostname), port);

      String request = method + " " + path + " HTTP/1.1\n";

      String requestBody = null;

      if (data != null){
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

        try {
          requestBody = writer.writeValueAsString(data);
        } catch (JsonProcessingException e) {
          connection.close();

          throw new RuntimeException("Invalid json data to send");
        }
      }

      if (requestBody != null) {
        headers.put("Content-Length", Integer.toString(requestBody.length()));
        headers.put("Content-Type", "application/json");
      }

      for (Map.Entry<String, String> entry : headers.entrySet()) {
        request += entry.getKey() + ": " + entry.getValue() + "\n";
      }

      request += "\n";

      if (requestBody != null) {
        request += requestBody;
      }

      BufferedWriter output = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

      output.write(request, 0, request.length());
      output.flush();

      BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      String startLine = input.readLine();

      String[] startLineElements = startLine.split(" ");

      if (startLineElements.length < 3) {
        connection.close();

        throw new RuntimeException("Invalid http response received");
      }

      String version = startLineElements[0];

      int statusCode;

      try {
        statusCode = Integer.parseInt(startLineElements[1]);
      } catch (NumberFormatException e) {
        connection.close();

        throw new RuntimeException("Invalid http response received");
      }

      if (statusCode < 200 || statusCode >= 300) {
        connection.close();

        throw new RuntimeException("Error http response received");
      }

      Map<String, String> responseHeaders = new HashMap<>();
      String nextLine;

      while ((nextLine = input.readLine()) != null && !nextLine.isEmpty()) {
        String[] headerElements = nextLine.split(": ");

        if (headerElements.length < 2) {
          continue;
        }

        responseHeaders.put(headerElements[0].toLowerCase().trim(), headerElements[1]);
      }

      int contentLength;

      try {
        contentLength = Integer.parseInt(responseHeaders.get("content-length"));
      } catch (NumberFormatException e) {
        contentLength = 0;
      }

      String body = null;

      if (contentLength > 0) {
        char[] buffer = new char[contentLength];

        input.read(buffer, 0, contentLength);

        body = String.copyValueOf(buffer);
      }

      connection.close();

      return new TcpHttpResponse(version, statusCode, responseHeaders, body);
    } catch (IOException e) {
      e.printStackTrace();

      throw new RuntimeException("Could not send tcp/http request");
    }
  }

  public TcpHttpResponse send(String method, String path, Object data) {
    return send(method, path, new HashMap<>(), data);
  }

  public class TcpHttpResponse {
    public final String version;
    public final int statusCode;
    private final Map<String, String> headers;
    private final String body;

    private TcpHttpResponse(String version, int statusCode, Map<String, String> headers, String body) {
      this.version = version;
      this.statusCode = statusCode;
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
  }
}
