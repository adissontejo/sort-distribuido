package ufrn.imd.project.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.73.0)",
    comments = "Source: services.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class QuicksortServiceGrpc {

  private QuicksortServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "project.QuicksortService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ufrn.imd.project.grpc.QuicksortMessage,
      ufrn.imd.project.grpc.SortResponseMessage> getSortMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Sort",
      requestType = ufrn.imd.project.grpc.QuicksortMessage.class,
      responseType = ufrn.imd.project.grpc.SortResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ufrn.imd.project.grpc.QuicksortMessage,
      ufrn.imd.project.grpc.SortResponseMessage> getSortMethod() {
    io.grpc.MethodDescriptor<ufrn.imd.project.grpc.QuicksortMessage, ufrn.imd.project.grpc.SortResponseMessage> getSortMethod;
    if ((getSortMethod = QuicksortServiceGrpc.getSortMethod) == null) {
      synchronized (QuicksortServiceGrpc.class) {
        if ((getSortMethod = QuicksortServiceGrpc.getSortMethod) == null) {
          QuicksortServiceGrpc.getSortMethod = getSortMethod =
              io.grpc.MethodDescriptor.<ufrn.imd.project.grpc.QuicksortMessage, ufrn.imd.project.grpc.SortResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Sort"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ufrn.imd.project.grpc.QuicksortMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ufrn.imd.project.grpc.SortResponseMessage.getDefaultInstance()))
              .setSchemaDescriptor(new QuicksortServiceMethodDescriptorSupplier("Sort"))
              .build();
        }
      }
    }
    return getSortMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QuicksortServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuicksortServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuicksortServiceStub>() {
        @java.lang.Override
        public QuicksortServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuicksortServiceStub(channel, callOptions);
        }
      };
    return QuicksortServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static QuicksortServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuicksortServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuicksortServiceBlockingV2Stub>() {
        @java.lang.Override
        public QuicksortServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuicksortServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return QuicksortServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QuicksortServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuicksortServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuicksortServiceBlockingStub>() {
        @java.lang.Override
        public QuicksortServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuicksortServiceBlockingStub(channel, callOptions);
        }
      };
    return QuicksortServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static QuicksortServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuicksortServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuicksortServiceFutureStub>() {
        @java.lang.Override
        public QuicksortServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuicksortServiceFutureStub(channel, callOptions);
        }
      };
    return QuicksortServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void sort(ufrn.imd.project.grpc.QuicksortMessage request,
        io.grpc.stub.StreamObserver<ufrn.imd.project.grpc.SortResponseMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSortMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service QuicksortService.
   */
  public static abstract class QuicksortServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return QuicksortServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service QuicksortService.
   */
  public static final class QuicksortServiceStub
      extends io.grpc.stub.AbstractAsyncStub<QuicksortServiceStub> {
    private QuicksortServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuicksortServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuicksortServiceStub(channel, callOptions);
    }

    /**
     */
    public void sort(ufrn.imd.project.grpc.QuicksortMessage request,
        io.grpc.stub.StreamObserver<ufrn.imd.project.grpc.SortResponseMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSortMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service QuicksortService.
   */
  public static final class QuicksortServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<QuicksortServiceBlockingV2Stub> {
    private QuicksortServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuicksortServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuicksortServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public ufrn.imd.project.grpc.SortResponseMessage sort(ufrn.imd.project.grpc.QuicksortMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSortMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service QuicksortService.
   */
  public static final class QuicksortServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<QuicksortServiceBlockingStub> {
    private QuicksortServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuicksortServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuicksortServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public ufrn.imd.project.grpc.SortResponseMessage sort(ufrn.imd.project.grpc.QuicksortMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSortMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service QuicksortService.
   */
  public static final class QuicksortServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<QuicksortServiceFutureStub> {
    private QuicksortServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuicksortServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuicksortServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ufrn.imd.project.grpc.SortResponseMessage> sort(
        ufrn.imd.project.grpc.QuicksortMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSortMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SORT = 0;

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
        case METHODID_SORT:
          serviceImpl.sort((ufrn.imd.project.grpc.QuicksortMessage) request,
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
          getSortMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ufrn.imd.project.grpc.QuicksortMessage,
              ufrn.imd.project.grpc.SortResponseMessage>(
                service, METHODID_SORT)))
        .build();
  }

  private static abstract class QuicksortServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    QuicksortServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ufrn.imd.project.grpc.Services.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("QuicksortService");
    }
  }

  private static final class QuicksortServiceFileDescriptorSupplier
      extends QuicksortServiceBaseDescriptorSupplier {
    QuicksortServiceFileDescriptorSupplier() {}
  }

  private static final class QuicksortServiceMethodDescriptorSupplier
      extends QuicksortServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    QuicksortServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (QuicksortServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new QuicksortServiceFileDescriptorSupplier())
              .addMethod(getSortMethod())
              .build();
        }
      }
    }
    return result;
  }
}
