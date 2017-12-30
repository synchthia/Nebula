package net.synchthia.api.nebula;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.6.1)",
    comments = "Source: nebulapb.proto")
public final class NebulaGrpc {

  private NebulaGrpc() {}

  public static final String SERVICE_NAME = "nebulapb.Nebula";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<net.synchthia.api.nebula.NebulaProtos.GetServerEntryRequest,
      net.synchthia.api.nebula.NebulaProtos.GetServerEntryResponse> METHOD_GET_SERVER_ENTRY =
      io.grpc.MethodDescriptor.<net.synchthia.api.nebula.NebulaProtos.GetServerEntryRequest, net.synchthia.api.nebula.NebulaProtos.GetServerEntryResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "nebulapb.Nebula", "GetServerEntry"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              net.synchthia.api.nebula.NebulaProtos.GetServerEntryRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              net.synchthia.api.nebula.NebulaProtos.GetServerEntryResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<net.synchthia.api.nebula.NebulaProtos.AddServerEntryRequest,
      net.synchthia.api.nebula.NebulaProtos.AddServerEntryResponse> METHOD_ADD_SERVER_ENTRY =
      io.grpc.MethodDescriptor.<net.synchthia.api.nebula.NebulaProtos.AddServerEntryRequest, net.synchthia.api.nebula.NebulaProtos.AddServerEntryResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "nebulapb.Nebula", "AddServerEntry"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              net.synchthia.api.nebula.NebulaProtos.AddServerEntryRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              net.synchthia.api.nebula.NebulaProtos.AddServerEntryResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryRequest,
      net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryResponse> METHOD_REMOVE_SERVER_ENTRY =
      io.grpc.MethodDescriptor.<net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryRequest, net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "nebulapb.Nebula", "RemoveServerEntry"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NebulaStub newStub(io.grpc.Channel channel) {
    return new NebulaStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NebulaBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new NebulaBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NebulaFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new NebulaFutureStub(channel);
  }

  /**
   */
  public static abstract class NebulaImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * API -&gt; Bungee (ServerEntry)
     * </pre>
     */
    public void getServerEntry(net.synchthia.api.nebula.NebulaProtos.GetServerEntryRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.GetServerEntryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SERVER_ENTRY, responseObserver);
    }

    /**
     * <pre>
     * API &lt;- App
     * </pre>
     */
    public void addServerEntry(net.synchthia.api.nebula.NebulaProtos.AddServerEntryRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.AddServerEntryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ADD_SERVER_ENTRY, responseObserver);
    }

    /**
     * <pre>
     * API &lt;- App
     * </pre>
     */
    public void removeServerEntry(net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REMOVE_SERVER_ENTRY, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_SERVER_ENTRY,
            asyncUnaryCall(
              new MethodHandlers<
                net.synchthia.api.nebula.NebulaProtos.GetServerEntryRequest,
                net.synchthia.api.nebula.NebulaProtos.GetServerEntryResponse>(
                  this, METHODID_GET_SERVER_ENTRY)))
          .addMethod(
            METHOD_ADD_SERVER_ENTRY,
            asyncUnaryCall(
              new MethodHandlers<
                net.synchthia.api.nebula.NebulaProtos.AddServerEntryRequest,
                net.synchthia.api.nebula.NebulaProtos.AddServerEntryResponse>(
                  this, METHODID_ADD_SERVER_ENTRY)))
          .addMethod(
            METHOD_REMOVE_SERVER_ENTRY,
            asyncUnaryCall(
              new MethodHandlers<
                net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryRequest,
                net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryResponse>(
                  this, METHODID_REMOVE_SERVER_ENTRY)))
          .build();
    }
  }

  /**
   */
  public static final class NebulaStub extends io.grpc.stub.AbstractStub<NebulaStub> {
    private NebulaStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NebulaStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NebulaStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NebulaStub(channel, callOptions);
    }

    /**
     * <pre>
     * API -&gt; Bungee (ServerEntry)
     * </pre>
     */
    public void getServerEntry(net.synchthia.api.nebula.NebulaProtos.GetServerEntryRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.GetServerEntryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SERVER_ENTRY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * API &lt;- App
     * </pre>
     */
    public void addServerEntry(net.synchthia.api.nebula.NebulaProtos.AddServerEntryRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.AddServerEntryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ADD_SERVER_ENTRY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * API &lt;- App
     * </pre>
     */
    public void removeServerEntry(net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REMOVE_SERVER_ENTRY, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class NebulaBlockingStub extends io.grpc.stub.AbstractStub<NebulaBlockingStub> {
    private NebulaBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NebulaBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NebulaBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NebulaBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * API -&gt; Bungee (ServerEntry)
     * </pre>
     */
    public net.synchthia.api.nebula.NebulaProtos.GetServerEntryResponse getServerEntry(net.synchthia.api.nebula.NebulaProtos.GetServerEntryRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SERVER_ENTRY, getCallOptions(), request);
    }

    /**
     * <pre>
     * API &lt;- App
     * </pre>
     */
    public net.synchthia.api.nebula.NebulaProtos.AddServerEntryResponse addServerEntry(net.synchthia.api.nebula.NebulaProtos.AddServerEntryRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ADD_SERVER_ENTRY, getCallOptions(), request);
    }

    /**
     * <pre>
     * API &lt;- App
     * </pre>
     */
    public net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryResponse removeServerEntry(net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REMOVE_SERVER_ENTRY, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class NebulaFutureStub extends io.grpc.stub.AbstractStub<NebulaFutureStub> {
    private NebulaFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NebulaFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NebulaFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NebulaFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * API -&gt; Bungee (ServerEntry)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<net.synchthia.api.nebula.NebulaProtos.GetServerEntryResponse> getServerEntry(
        net.synchthia.api.nebula.NebulaProtos.GetServerEntryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SERVER_ENTRY, getCallOptions()), request);
    }

    /**
     * <pre>
     * API &lt;- App
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<net.synchthia.api.nebula.NebulaProtos.AddServerEntryResponse> addServerEntry(
        net.synchthia.api.nebula.NebulaProtos.AddServerEntryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ADD_SERVER_ENTRY, getCallOptions()), request);
    }

    /**
     * <pre>
     * API &lt;- App
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryResponse> removeServerEntry(
        net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REMOVE_SERVER_ENTRY, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SERVER_ENTRY = 0;
  private static final int METHODID_ADD_SERVER_ENTRY = 1;
  private static final int METHODID_REMOVE_SERVER_ENTRY = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final NebulaImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(NebulaImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_SERVER_ENTRY:
          serviceImpl.getServerEntry((net.synchthia.api.nebula.NebulaProtos.GetServerEntryRequest) request,
              (io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.GetServerEntryResponse>) responseObserver);
          break;
        case METHODID_ADD_SERVER_ENTRY:
          serviceImpl.addServerEntry((net.synchthia.api.nebula.NebulaProtos.AddServerEntryRequest) request,
              (io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.AddServerEntryResponse>) responseObserver);
          break;
        case METHODID_REMOVE_SERVER_ENTRY:
          serviceImpl.removeServerEntry((net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryRequest) request,
              (io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryResponse>) responseObserver);
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

  private static final class NebulaDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return net.synchthia.api.nebula.NebulaProtos.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NebulaGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NebulaDescriptorSupplier())
              .addMethod(METHOD_GET_SERVER_ENTRY)
              .addMethod(METHOD_ADD_SERVER_ENTRY)
              .addMethod(METHOD_REMOVE_SERVER_ENTRY)
              .build();
        }
      }
    }
    return result;
  }
}
