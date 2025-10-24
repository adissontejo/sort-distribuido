package ufrn.imd.project.mergesort.protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import ufrn.imd.project.dtos.MergesortRequest;
import ufrn.imd.project.grpc.MergesortMessage;
import ufrn.imd.project.grpc.SortResponseMessage;
import ufrn.imd.project.grpc.MergesortServiceGrpc.MergesortServiceImplBase;

public class MergesortGrpcStrategy implements MergesortProtocolStrategy {
  @Override
  public void listen(int port, RequestListener listener) {
    try {
      Server server = ServerBuilder
        .forPort(port)
        .addService(new MergesortServiceImpl(listener))
        .build();

      server.start();
      server.awaitTermination();
    } catch (IOException e) {
      throw new RuntimeException("Could not listen to grpc requests");
    } catch (InterruptedException e) {
      throw new RuntimeException("Could not listen to grpc requests");
    }
  }

  private static class MergesortServiceImpl extends MergesortServiceImplBase {
    private RequestListener listener;

    public MergesortServiceImpl(RequestListener listener) {
      this.listener = listener;
    }

    @Override
    public void sort(MergesortMessage request, StreamObserver<SortResponseMessage> responseObserver) {
      List<Integer> data = new ArrayList<>(request.getDataList());

      listener.execute(
        new MergesortRequest(data),
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
