package ufrn.imd.project.gateway.protocol;

import java.io.IOException;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.dtos.QuicksortRequest;
import ufrn.imd.project.grpc.QuicksortMessage;
import ufrn.imd.project.grpc.QuicksortServiceGrpc;
import ufrn.imd.project.grpc.SortResponseMessage;
import ufrn.imd.project.grpc.GatewayServiceGrpc.GatewayServiceImplBase;
import ufrn.imd.project.grpc.QuicksortServiceGrpc.QuicksortServiceBlockingStub;

public class GatewayGrpcStrategy implements GatewayProtocolStrategy {
  @Override
  public void listen(int port, ListenCallback callback) throws IOException, InterruptedException {
    Server server = ServerBuilder.forPort(port).addService(new GatewayServiceImpl(callback)).build();

    server.start();
    server.awaitTermination();
  }

  @Override
  public void sendToQuicksort(ComponentInstance instance, QuicksortRequest request) {
    ManagedChannel channel = ManagedChannelBuilder
      .forAddress(instance.hostname(), instance.port())
      .usePlaintext()
      .build();

    QuicksortServiceBlockingStub stub = QuicksortServiceGrpc.newBlockingStub(channel);

    QuicksortMessage message =
      QuicksortMessage.newBuilder()
        .addAllData(request.data())
        .build();

    stub.sort(message);

    channel.shutdown();
  }

  private class GatewayServiceImpl extends GatewayServiceImplBase {
    private ListenCallback callback;

    public GatewayServiceImpl(ListenCallback callback) {
      this.callback = callback;
    }

    @Override
    public void quicksort(QuicksortMessage request, StreamObserver<SortResponseMessage> responseObserver) {
      callback.execute();

      responseObserver.onNext(
        SortResponseMessage.newBuilder()
          .addAllData(request.getDataList())
          .build()
      );
      responseObserver.onCompleted();
    }
  }
}
