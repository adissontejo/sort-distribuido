package ufrn.imd.project.gateway.protocol;

import java.io.IOException;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.dtos.MergesortRequest;
import ufrn.imd.project.dtos.ParallelSortCriteria;
import ufrn.imd.project.dtos.ParallelSortRequest;
import ufrn.imd.project.dtos.QuicksortRequest;
import ufrn.imd.project.dtos.SortResponse;
import ufrn.imd.project.grpc.MergesortMessage;
import ufrn.imd.project.grpc.MergesortServiceGrpc;
import ufrn.imd.project.grpc.ParallelSortCriteriaGrpc;
import ufrn.imd.project.grpc.ParallelSortMessage;
import ufrn.imd.project.grpc.ParallelSortResponseMessage;
import ufrn.imd.project.grpc.QuicksortMessage;
import ufrn.imd.project.grpc.QuicksortServiceGrpc;
import ufrn.imd.project.grpc.SortResponseMessage;
import ufrn.imd.project.grpc.GatewayServiceGrpc.GatewayServiceImplBase;
import ufrn.imd.project.grpc.MergesortServiceGrpc.MergesortServiceBlockingStub;
import ufrn.imd.project.grpc.QuicksortServiceGrpc.QuicksortServiceBlockingStub;

public class GatewayGrpcStrategy implements GatewayProtocolStrategy {
  @Override
  public void listen(int port, Router router) throws IOException, InterruptedException {
    Server server = ServerBuilder.forPort(port).addService(new GatewayServiceImpl(router)).build();

    server.start();
    server.awaitTermination();
  }

  @Override
  public SortResponse sendToQuicksort(ComponentInstance instance, QuicksortRequest request) {
    ManagedChannel channel = ManagedChannelBuilder
      .forAddress(instance.hostname(), instance.port())
      .usePlaintext()
      .build();

    QuicksortServiceBlockingStub stub = QuicksortServiceGrpc.newBlockingStub(channel);

    QuicksortMessage message =
      QuicksortMessage.newBuilder()
        .addAllData(request.data())
        .build();

    SortResponseMessage response = stub.sort(message);

    channel.shutdown();

    return new SortResponse(response.getDataList(), response.getTime());
  }

  @Override
  public SortResponse sendToMergesort(ComponentInstance instance, MergesortRequest request) {
    ManagedChannel channel = ManagedChannelBuilder
      .forAddress(instance.hostname(), instance.port())
      .usePlaintext()
      .build();

    MergesortServiceBlockingStub stub = MergesortServiceGrpc.newBlockingStub(channel);

    MergesortMessage message =
      MergesortMessage.newBuilder()
        .addAllData(request.data())
        .build();

    SortResponseMessage response = stub.sort(message);

    channel.shutdown();

    return new SortResponse(response.getDataList(), response.getTime());
  }

  private class GatewayServiceImpl extends GatewayServiceImplBase {
    private Router router;

    public GatewayServiceImpl(Router router) {
      this.router = router;
    }

    @Override
    public void quicksort(QuicksortMessage request, StreamObserver<SortResponseMessage> responseObserver) {
      router.onQuicksortRequest(
        new QuicksortRequest(request.getDataList()),
        response -> {
          responseObserver.onNext(
            SortResponseMessage.newBuilder()
              .addAllData(response.data())
              .setTime(response.time())
              .build()
          );
          responseObserver.onCompleted();
        }
      );
    }

    @Override
    public void mergesort(MergesortMessage request, StreamObserver<SortResponseMessage> responseObserver) {
      router.onMergesortRequest(
        new MergesortRequest(request.getDataList()),
        response -> {
          responseObserver.onNext(
            SortResponseMessage.newBuilder()
              .addAllData(response.data())
              .setTime(response.time())
              .build()
          );
          responseObserver.onCompleted();
        }
      );
    }

    @Override
    public void parallelSort(ParallelSortMessage request, StreamObserver<ParallelSortResponseMessage> responseObserver) {
      ParallelSortCriteria criteria = request.getCriteria() == ParallelSortCriteriaGrpc.FIRST
        ? ParallelSortCriteria.FIRST
        : ParallelSortCriteria.ALL;

      router.onParallelSortRequest(
        new ParallelSortRequest(request.getDataList(), criteria),
        response -> {
          var builder = ParallelSortResponseMessage.newBuilder();

          if (response.quicksort() != null) {
            builder.setQuicksort(
              SortResponseMessage.newBuilder()
                .addAllData(response.quicksort().data())
                .setTime(response.quicksort().time())
            );
          }

          if (response.mergesort() != null) {
            builder.setMergesort(
              SortResponseMessage.newBuilder()
                .addAllData(response.mergesort().data())
                .setTime(response.mergesort().time())
            );
          }

          responseObserver.onNext(builder.build());
          responseObserver.onCompleted();
        }
      );
    }
  }
}
