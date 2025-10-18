package ufrn.imd.project.hearbeat.protocol;

import java.io.IOException;

import com.google.protobuf.Empty;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.grpc.HeartbeatServiceGrpc;
import ufrn.imd.project.grpc.HeartbeatServiceGrpc.HeartbeatServiceBlockingStub;
import ufrn.imd.project.grpc.HeartbeatServiceGrpc.HeartbeatServiceImplBase;
import ufrn.imd.project.grpc.InstanceMessage;

public class HeartbeatGrpcStrategy implements HeartbeatProtocolStrategy {
  @Override
  public void emit(String hostname, int port, ComponentInstance self) {
    ManagedChannel channel = ManagedChannelBuilder.forAddress(hostname, port).usePlaintext().build();

    HeartbeatServiceBlockingStub stub = HeartbeatServiceGrpc.newBlockingStub(channel);

    InstanceMessage message =
      InstanceMessage.newBuilder()
        .setComponentKey(self.componentKey())
        .setHostname(self.hostname())
        .setPort(self.port())
        .build();

    stub.receive(message);

    channel.shutdown();
  }

  @Override
  public void listen(int port, ListenCallback callback) throws IOException, InterruptedException {
    Server server = ServerBuilder.forPort(port).addService(new HeartbeatServiceImpl(callback)).build();

    server.start();
    server.awaitTermination();
  }

  private static class HeartbeatServiceImpl extends HeartbeatServiceImplBase {
    private final ListenCallback callback;

    public HeartbeatServiceImpl(ListenCallback callback) {
      this.callback = callback;
    }

    @Override
    public void receive(InstanceMessage request, StreamObserver<Empty> responseObserver) {
      callback.execute(
        new ComponentInstance(
          request.getComponentKey(),
          request.getHostname(),
          request.getPort()
        )
      );

      responseObserver.onNext(null);
      responseObserver.onCompleted();
    }
  }
}
