package ufrn.imd.project.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.73.0)",
    comments = "Source: services.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class HeartbeatServiceGrpc {

  private HeartbeatServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "project.HeartbeatService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ufrn.imd.project.grpc.InstanceMessage,
      com.google.protobuf.Empty> getReceiveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Receive",
      requestType = ufrn.imd.project.grpc.InstanceMessage.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ufrn.imd.project.grpc.InstanceMessage,
      com.google.protobuf.Empty> getReceiveMethod() {
    io.grpc.MethodDescriptor<ufrn.imd.project.grpc.InstanceMessage, com.google.protobuf.Empty> getReceiveMethod;
    if ((getReceiveMethod = HeartbeatServiceGrpc.getReceiveMethod) == null) {
      synchronized (HeartbeatServiceGrpc.class) {
        if ((getReceiveMethod = HeartbeatServiceGrpc.getReceiveMethod) == null) {
          HeartbeatServiceGrpc.getReceiveMethod = getReceiveMethod =
              io.grpc.MethodDescriptor.<ufrn.imd.project.grpc.InstanceMessage, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Receive"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ufrn.imd.project.grpc.InstanceMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new HeartbeatServiceMethodDescriptorSupplier("Receive"))
              .build();
        }
      }
    }
    return getReceiveMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HeartbeatServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HeartbeatServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HeartbeatServiceStub>() {
        @java.lang.Override
        public HeartbeatServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HeartbeatServiceStub(channel, callOptions);
        }
      };
    return HeartbeatServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static HeartbeatServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HeartbeatServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HeartbeatServiceBlockingV2Stub>() {
        @java.lang.Override
        public HeartbeatServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HeartbeatServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return HeartbeatServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HeartbeatServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HeartbeatServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HeartbeatServiceBlockingStub>() {
        @java.lang.Override
        public HeartbeatServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HeartbeatServiceBlockingStub(channel, callOptions);
        }
      };
    return HeartbeatServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HeartbeatServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HeartbeatServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HeartbeatServiceFutureStub>() {
        @java.lang.Override
        public HeartbeatServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HeartbeatServiceFutureStub(channel, callOptions);
        }
      };
    return HeartbeatServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void receive(ufrn.imd.project.grpc.InstanceMessage request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReceiveMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service HeartbeatService.
   */
  public static abstract class HeartbeatServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return HeartbeatServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service HeartbeatService.
   */
  public static final class HeartbeatServiceStub
      extends io.grpc.stub.AbstractAsyncStub<HeartbeatServiceStub> {
    private HeartbeatServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HeartbeatServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HeartbeatServiceStub(channel, callOptions);
    }

    /**
     */
    public void receive(ufrn.imd.project.grpc.InstanceMessage request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReceiveMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service HeartbeatService.
   */
  public static final class HeartbeatServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<HeartbeatServiceBlockingV2Stub> {
    private HeartbeatServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HeartbeatServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HeartbeatServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty receive(ufrn.imd.project.grpc.InstanceMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReceiveMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service HeartbeatService.
   */
  public static final class HeartbeatServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<HeartbeatServiceBlockingStub> {
    private HeartbeatServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HeartbeatServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HeartbeatServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty receive(ufrn.imd.project.grpc.InstanceMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReceiveMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service HeartbeatService.
   */
  public static final class HeartbeatServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<HeartbeatServiceFutureStub> {
    private HeartbeatServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HeartbeatServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HeartbeatServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> receive(
        ufrn.imd.project.grpc.InstanceMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReceiveMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RECEIVE = 0;

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
        case METHODID_RECEIVE:
          serviceImpl.receive((ufrn.imd.project.grpc.InstanceMessage) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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
          getReceiveMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ufrn.imd.project.grpc.InstanceMessage,
              com.google.protobuf.Empty>(
                service, METHODID_RECEIVE)))
        .build();
  }

  private static abstract class HeartbeatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HeartbeatServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ufrn.imd.project.grpc.Services.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HeartbeatService");
    }
  }

  private static final class HeartbeatServiceFileDescriptorSupplier
      extends HeartbeatServiceBaseDescriptorSupplier {
    HeartbeatServiceFileDescriptorSupplier() {}
  }

  private static final class HeartbeatServiceMethodDescriptorSupplier
      extends HeartbeatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    HeartbeatServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (HeartbeatServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HeartbeatServiceFileDescriptorSupplier())
              .addMethod(getReceiveMethod())
              .build();
        }
      }
    }
    return result;
  }
}
