package ufrn.imd.project.quicksort.protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import ufrn.imd.project.dtos.QuicksortRequest;
import ufrn.imd.project.dtos.SortResponse;

public class QuicksortTcpStrategy implements QuicksortProtocolStrategy {
  @Override
  public void listen(int port, ListenCallback callback) throws IOException {
    ServerSocket serverSocket = new ServerSocket(port);

    try {
      while (true) {
        Socket connection = serverSocket.accept();

        ObjectInputStream inputStream = new ObjectInputStream(connection.getInputStream());

        String message;

        try {
          message = ((String) inputStream.readObject()).trim();
        } catch (ClassNotFoundException e) {
          connection.close();

          continue;
        }

        callback.execute(
          new QuicksortRequest(),
          new Reply() {
            public void send(SortResponse response) {
              try {
                ObjectOutputStream outputStream = new ObjectOutputStream(connection.getOutputStream());

                outputStream.writeObject(response);
                outputStream.flush();

                connection.close();
              } catch (IOException e) {
                System.out.println("Could not send response to gateway.");
              }
            };
          }
        );

        connection.close();
      }
    } catch (IOException e) {
      serverSocket.close();

      throw e;
    }
  }
}
