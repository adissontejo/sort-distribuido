package ufrn.imd.project.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.73.0)",
    comments = "Source: services.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class MergesortServiceGrpc {

  private MergesortServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "project.MergesortService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ufrn.imd.project.grpc.MergesortMessage,
      ufrn.imd.project.grpc.SortResponseMessage> getSortMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Sort",
      requestType = ufrn.imd.project.grpc.MergesortMessage.class,
      responseType = ufrn.imd.project.grpc.SortResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ufrn.imd.project.grpc.MergesortMessage,
      ufrn.imd.project.grpc.SortResponseMessage> getSortMethod() {
    io.grpc.MethodDescriptor<ufrn.imd.project.grpc.MergesortMessage, ufrn.imd.project.grpc.SortResponseMessage> getSortMethod;
    if ((getSortMethod = MergesortServiceGrpc.getSortMethod) == null) {
      synchronized (MergesortServiceGrpc.class) {
        if ((getSortMethod = MergesortServiceGrpc.getSortMethod) == null) {
          MergesortServiceGrpc.getSortMethod = getSortMethod =
              io.grpc.MethodDescriptor.<ufrn.imd.project.grpc.MergesortMessage, ufrn.imd.project.grpc.SortResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Sort"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ufrn.imd.project.grpc.MergesortMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ufrn.imd.project.grpc.SortResponseMessage.getDefaultInstance()))
              .setSchemaDescriptor(new MergesortServiceMethodDescriptorSupplier("Sort"))
              .build();
        }
      }
    }
    return getSortMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MergesortServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MergesortServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MergesortServiceStub>() {
        @java.lang.Override
        public MergesortServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MergesortServiceStub(channel, callOptions);
        }
      };
    return MergesortServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static MergesortServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MergesortServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MergesortServiceBlockingV2Stub>() {
        @java.lang.Override
        public MergesortServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MergesortServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return MergesortServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MergesortServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MergesortServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MergesortServiceBlockingStub>() {
        @java.lang.Override
        public MergesortServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MergesortServiceBlockingStub(channel, callOptions);
        }
      };
    return MergesortServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MergesortServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MergesortServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MergesortServiceFutureStub>() {
        @java.lang.Override
        public MergesortServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MergesortServiceFutureStub(channel, callOptions);
        }
      };
    return MergesortServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void sort(ufrn.imd.project.grpc.MergesortMessage request,
        io.grpc.stub.StreamObserver<ufrn.imd.project.grpc.SortResponseMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSortMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service MergesortService.
   */
  public static abstract class MergesortServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return MergesortServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service MergesortService.
   */
  public static final class MergesortServiceStub
      extends io.grpc.stub.AbstractAsyncStub<MergesortServiceStub> {
    private MergesortServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MergesortServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MergesortServiceStub(channel, callOptions);
    }

    /**
     */
    public void sort(ufrn.imd.project.grpc.MergesortMessage request,
        io.grpc.stub.StreamObserver<ufrn.imd.project.grpc.SortResponseMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSortMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service MergesortService.
   */
  public static final class MergesortServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<MergesortServiceBlockingV2Stub> {
    private MergesortServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MergesortServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MergesortServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public ufrn.imd.project.grpc.SortResponseMessage sort(ufrn.imd.project.grpc.MergesortMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSortMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service MergesortService.
   */
  public static final class MergesortServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<MergesortServiceBlockingStub> {
    private MergesortServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MergesortServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MergesortServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public ufrn.imd.project.grpc.SortResponseMessage sort(ufrn.imd.project.grpc.MergesortMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSortMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service MergesortService.
   */
  public static final class MergesortServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<MergesortServiceFutureStub> {
    private MergesortServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MergesortServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MergesortServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ufrn.imd.project.grpc.SortResponseMessage> sort(
        ufrn.imd.project.grpc.MergesortMessage request) {
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
          serviceImpl.sort((ufrn.imd.project.grpc.MergesortMessage) request,
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
              ufrn.imd.project.grpc.MergesortMessage,
              ufrn.imd.project.grpc.SortResponseMessage>(
                service, METHODID_SORT)))
        .build();
  }

  private static abstract class MergesortServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MergesortServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ufrn.imd.project.grpc.Services.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MergesortService");
    }
  }

  private static final class MergesortServiceFileDescriptorSupplier
      extends MergesortServiceBaseDescriptorSupplier {
    MergesortServiceFileDescriptorSupplier() {}
  }

  private static final class MergesortServiceMethodDescriptorSupplier
      extends MergesortServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    MergesortServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (MergesortServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MergesortServiceFileDescriptorSupplier())
              .addMethod(getSortMethod())
              .build();
        }
      }
    }
    return result;
  }
}
