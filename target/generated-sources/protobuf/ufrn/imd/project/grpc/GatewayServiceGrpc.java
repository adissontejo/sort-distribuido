package ufrn.imd.project.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.73.0)",
    comments = "Source: services.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class GatewayServiceGrpc {

  private GatewayServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "project.GatewayService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ufrn.imd.project.grpc.QuicksortMessage,
      ufrn.imd.project.grpc.SortResponseMessage> getQuicksortMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Quicksort",
      requestType = ufrn.imd.project.grpc.QuicksortMessage.class,
      responseType = ufrn.imd.project.grpc.SortResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ufrn.imd.project.grpc.QuicksortMessage,
      ufrn.imd.project.grpc.SortResponseMessage> getQuicksortMethod() {
    io.grpc.MethodDescriptor<ufrn.imd.project.grpc.QuicksortMessage, ufrn.imd.project.grpc.SortResponseMessage> getQuicksortMethod;
    if ((getQuicksortMethod = GatewayServiceGrpc.getQuicksortMethod) == null) {
      synchronized (GatewayServiceGrpc.class) {
        if ((getQuicksortMethod = GatewayServiceGrpc.getQuicksortMethod) == null) {
          GatewayServiceGrpc.getQuicksortMethod = getQuicksortMethod =
              io.grpc.MethodDescriptor.<ufrn.imd.project.grpc.QuicksortMessage, ufrn.imd.project.grpc.SortResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Quicksort"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ufrn.imd.project.grpc.QuicksortMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ufrn.imd.project.grpc.SortResponseMessage.getDefaultInstance()))
              .setSchemaDescriptor(new GatewayServiceMethodDescriptorSupplier("Quicksort"))
              .build();
        }
      }
    }
    return getQuicksortMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GatewayServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GatewayServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GatewayServiceStub>() {
        @java.lang.Override
        public GatewayServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GatewayServiceStub(channel, callOptions);
        }
      };
    return GatewayServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static GatewayServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GatewayServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GatewayServiceBlockingV2Stub>() {
        @java.lang.Override
        public GatewayServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GatewayServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return GatewayServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GatewayServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GatewayServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GatewayServiceBlockingStub>() {
        @java.lang.Override
        public GatewayServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GatewayServiceBlockingStub(channel, callOptions);
        }
      };
    return GatewayServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GatewayServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GatewayServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GatewayServiceFutureStub>() {
        @java.lang.Override
        public GatewayServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GatewayServiceFutureStub(channel, callOptions);
        }
      };
    return GatewayServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void quicksort(ufrn.imd.project.grpc.QuicksortMessage request,
        io.grpc.stub.StreamObserver<ufrn.imd.project.grpc.SortResponseMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQuicksortMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service GatewayService.
   */
  public static abstract class GatewayServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return GatewayServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service GatewayService.
   */
  public static final class GatewayServiceStub
      extends io.grpc.stub.AbstractAsyncStub<GatewayServiceStub> {
    private GatewayServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GatewayServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GatewayServiceStub(channel, callOptions);
    }

    /**
     */
    public void quicksort(ufrn.imd.project.grpc.QuicksortMessage request,
        io.grpc.stub.StreamObserver<ufrn.imd.project.grpc.SortResponseMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQuicksortMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service GatewayService.
   */
  public static final class GatewayServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<GatewayServiceBlockingV2Stub> {
    private GatewayServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GatewayServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GatewayServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public ufrn.imd.project.grpc.SortResponseMessage quicksort(ufrn.imd.project.grpc.QuicksortMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQuicksortMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service GatewayService.
   */
  public static final class GatewayServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<GatewayServiceBlockingStub> {
    private GatewayServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GatewayServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GatewayServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public ufrn.imd.project.grpc.SortResponseMessage quicksort(ufrn.imd.project.grpc.QuicksortMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQuicksortMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service GatewayService.
   */
  public static final class GatewayServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<GatewayServiceFutureStub> {
    private GatewayServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GatewayServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GatewayServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ufrn.imd.project.grpc.SortResponseMessage> quicksort(
        ufrn.imd.project.grpc.QuicksortMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQuicksortMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_QUICKSORT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_QUICKSORT:
          serviceImpl.quicksort((ufrn.imd.project.grpc.QuicksortMessage) request,
              (io.grpc.stub.StreamObserver<ufrn.imd.project.grpc.SortResponseMessage>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getQuicksortMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ufrn.imd.project.grpc.QuicksortMessage,
              ufrn.imd.project.grpc.SortResponseMessage>(
                service, METHODID_QUICKSORT)))
        .build();
  }

  private static abstract class GatewayServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GatewayServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ufrn.imd.project.grpc.Services.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("GatewayService");
    }
  }

  private static final class GatewayServiceFileDescriptorSupplier
      extends GatewayServiceBaseDescriptorSupplier {
    GatewayServiceFileDescriptorSupplier() {}
  }

  private static final class GatewayServiceMethodDescriptorSupplier
      extends GatewayServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    GatewayServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GatewayServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GatewayServiceFileDescriptorSupplier())
              .addMethod(getQuicksortMethod())
              .build();
        }
      }
    }
    return result;
  }
}
