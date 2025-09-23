package ufrn.imd.project.quicksort.protocol;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import ufrn.imd.project.dtos.SortResponse;
import ufrn.imd.project.grpc.QuicksortMessage;
import ufrn.imd.project.grpc.SortResponseMessage;
import ufrn.imd.project.grpc.QuicksortServiceGrpc.QuicksortServiceImplBase;

public class QuicksortGrpcStrategy implements QuicksortProtocolStrategy {
  @Override
  public void listen(int port, ListenCallback callback) throws IOException, InterruptedException {
    Server server = ServerBuilder.forPort(port).addService(new QuicksortServiceImpl(callback)).build();

    server.start();
    server.awaitTermination();
  }

  private class QuicksortServiceImpl extends QuicksortServiceImplBase {
    private ListenCallback callback;

    public QuicksortServiceImpl(ListenCallback callback) {
      this.callback = callback;
    }

    @Override
    public void sort(QuicksortMessage request, StreamObserver<SortResponseMessage> responseObserver) {
      callback.execute(new Reply() {
        @Override
        public void send(SortResponse response) {
          responseObserver.onNext(
            SortResponseMessage.newBuilder()
              .addAllData(request.getDataList())
              .build()
          );
          responseObserver.onCompleted();
        }
      });
    }
  }
}
