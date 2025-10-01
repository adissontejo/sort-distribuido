package ufrn.imd.project.quicksort.protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import ufrn.imd.project.dtos.QuicksortRequest;
import ufrn.imd.project.grpc.QuicksortMessage;
import ufrn.imd.project.grpc.SortResponseMessage;
import ufrn.imd.project.grpc.QuicksortServiceGrpc.QuicksortServiceImplBase;

public class QuicksortGrpcStrategy implements QuicksortProtocolStrategy {
  @Override
  public void listen(int port, RequestListener listener) throws IOException, InterruptedException {
    Server server = ServerBuilder.forPort(port).addService(new QuicksortServiceImpl(listener)).build();

    server.start();
    server.awaitTermination();
  }

  private class QuicksortServiceImpl extends QuicksortServiceImplBase {
    private RequestListener listener;

    public QuicksortServiceImpl(RequestListener listener) {
      this.listener = listener;
    }

    @Override
    public void sort(QuicksortMessage request, StreamObserver<SortResponseMessage> responseObserver) {
      List<Integer> data = new ArrayList<>(request.getDataList());

      listener.execute(
        new QuicksortRequest(data),
        response -> {
          responseObserver.onNext(
            SortResponseMessage.newBuilder()
              .addAllData(response.data())
              .setNanoseconds(response.nanoseconds())
              .build()
          );
          responseObserver.onCompleted();
        }
      );
    }
  }
}
