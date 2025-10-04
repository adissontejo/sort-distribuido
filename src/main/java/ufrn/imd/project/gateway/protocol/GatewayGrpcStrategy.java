package ufrn.imd.project.gateway.protocol;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import ufrn.imd.project.dtos.ComponentInstance;
import ufrn.imd.project.dtos.MergesortRequest;
import ufrn.imd.project.dtos.ParallelSortCriteria;
import ufrn.imd.project.dtos.ParallelSortRequest;
import ufrn.imd.project.dtos.ParallelSortResponse;
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
  public void listen(int port, Router router, ExecutorService executor) {
    try {
      Server server = ServerBuilder
        .forPort(port)
        .executor(executor)
        .addService(new GatewayServiceImpl(router)).build();


      server.start();
      server.awaitTermination();
    } catch (IOException e) {
      throw new RuntimeException("Could not listen to grpc requests");
    } catch (InterruptedException e) {
      throw new RuntimeException("Could not listen to grpc requests");
    }
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

    return new SortResponse(response.getDataList(), response.getNanoseconds());
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

    return new SortResponse(response.getDataList(), response.getNanoseconds());
  }

  private class GatewayServiceImpl extends GatewayServiceImplBase {
    private Router router;

    public GatewayServiceImpl(Router router) {
      this.router = router;
    }

    @Override
    public void quicksort(QuicksortMessage request, StreamObserver<SortResponseMessage> responseObserver) {
      if (request.getDataList() == null) {
        responseObserver.onError(new RuntimeException("data is required"));
      } else {
        router.onQuicksortRequest(
          new QuicksortRequest(request.getDataList()),
          new Reply<SortResponse>() {
            @Override
            public void send(SortResponse response) {
              responseObserver.onNext(
                SortResponseMessage.newBuilder()
                  .addAllData(response.data())
                  .setNanoseconds(response.nanoseconds())
                  .build()
              );
              responseObserver.onCompleted();
            };

            @Override
            public void error(String message, boolean isValidationError) {
              responseObserver.onError(
                new RuntimeException((isValidationError ? "Bad Request: " : "Internal Server Error: ") + message)
              );
            }
          }
        );
      }
    }

    @Override
    public void mergesort(MergesortMessage request, StreamObserver<SortResponseMessage> responseObserver) {
      if (request.getDataList() == null) {
        responseObserver.onError(new RuntimeException("data is required"));
      } else {
        router.onMergesortRequest(
          new MergesortRequest(request.getDataList()),
          new Reply<SortResponse>() {
            @Override
            public void send(SortResponse response) {
              responseObserver.onNext(
                SortResponseMessage.newBuilder()
                  .addAllData(response.data())
                  .setNanoseconds(response.nanoseconds())
                  .build()
              );
              responseObserver.onCompleted();
            };

            @Override
            public void error(String message, boolean isValidationError) {
              responseObserver.onError(
                new RuntimeException((isValidationError ? "Bad Request: " : "Internal Server Error: ") + message)
              );
            }
          }
        );
      }
    }

    @Override
    public void parallelSort(ParallelSortMessage request, StreamObserver<ParallelSortResponseMessage> responseObserver) {
      ParallelSortCriteria criteria = request.getCriteria() == ParallelSortCriteriaGrpc.FIRST
        ? ParallelSortCriteria.FIRST
        : ParallelSortCriteria.ALL;

      if (request.getDataList() == null) {
        responseObserver.onError(new RuntimeException("data is required"));
      } else {
        router.onParallelSortRequest(
          new ParallelSortRequest(request.getDataList(), criteria),
          new Reply<ParallelSortResponse>() {
            @Override
            public void send(ParallelSortResponse response) {
              var builder = ParallelSortResponseMessage.newBuilder();

              if (response.quicksort() != null) {
                builder.setQuicksort(
                  SortResponseMessage.newBuilder()
                    .addAllData(response.quicksort().data())
                    .setNanoseconds(response.quicksort().nanoseconds())
                );
              }

              if (response.mergesort() != null) {
                builder.setMergesort(
                  SortResponseMessage.newBuilder()
                    .addAllData(response.mergesort().data())
                    .setNanoseconds(response.mergesort().nanoseconds())
                );
              }

              responseObserver.onNext(builder.build());
              responseObserver.onCompleted();
            };

            @Override
            public void error(String message, boolean isValidationError) {
              responseObserver.onError(
                new RuntimeException((isValidationError ? "Bad Request: " : "Internal Server Error: ") + message)
              );
            }
          }
        );
      }
    }
  }
}
