package ufrn.imd.project.hearbeat.protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import ufrn.imd.project.dtos.ComponentInstance;

public class HeartbeatTcpStrategy implements HeartbeatProtocolStrategy {
  @Override
  public void emit(String hostname, int port, ComponentInstance self) throws IOException {
    Socket connection = new Socket(hostname, port);

    String message = self.componentKey() + " " + self.hostname() + " " + self.port();

    ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
		output.writeObject(message);
		output.flush();

    connection.close();
  }

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

        connection.close();
      }
    } catch (IOException e) {
      serverSocket.close();

      throw e;
    }
  }
}
