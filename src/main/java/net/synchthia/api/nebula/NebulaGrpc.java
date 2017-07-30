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
    value = "by gRPC proto compiler (version 1.0.2)",
    comments = "Source: nebulapb.proto")
public class NebulaGrpc {

  private NebulaGrpc() {}

  public static final String SERVICE_NAME = "nebulapb.Nebula";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<net.synchthia.api.nebula.NebulaProtos.Empty,
      net.synchthia.api.nebula.NebulaProtos.Empty> METHOD_PING =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "nebulapb.Nebula", "Ping"),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.Empty.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.Empty.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<net.synchthia.api.nebula.NebulaProtos.StreamRequest,
      net.synchthia.api.nebula.NebulaProtos.EntryStreamResponse> METHOD_ENTRY_STREAM =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "nebulapb.Nebula", "EntryStream"),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.StreamRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.EntryStreamResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<net.synchthia.api.nebula.NebulaProtos.QuitStreamRequest,
      net.synchthia.api.nebula.NebulaProtos.Empty> METHOD_QUIT_STREAM =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "nebulapb.Nebula", "QuitStream"),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.QuitStreamRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.Empty.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<net.synchthia.api.nebula.NebulaProtos.GetServerEntryRequest,
      net.synchthia.api.nebula.NebulaProtos.GetServerEntryResponse> METHOD_GET_SERVER_ENTRY =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "nebulapb.Nebula", "GetServerEntry"),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.GetServerEntryRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.GetServerEntryResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<net.synchthia.api.nebula.NebulaProtos.AddServerEntryRequest,
      net.synchthia.api.nebula.NebulaProtos.AddServerEntryResponse> METHOD_ADD_SERVER_ENTRY =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "nebulapb.Nebula", "AddServerEntry"),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.AddServerEntryRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.AddServerEntryResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryRequest,
      net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryResponse> METHOD_REMOVE_SERVER_ENTRY =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "nebulapb.Nebula", "RemoveServerEntry"),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.RemoveServerEntryResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<net.synchthia.api.nebula.NebulaProtos.PushStatusRequest,
      net.synchthia.api.nebula.NebulaProtos.PushStatusResponse> METHOD_PUSH_STATUS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "nebulapb.Nebula", "PushStatus"),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.PushStatusRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.PushStatusResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<net.synchthia.api.nebula.NebulaProtos.FetchStatusRequest,
      net.synchthia.api.nebula.NebulaProtos.FetchStatusResponse> METHOD_FETCH_STATUS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "nebulapb.Nebula", "FetchStatus"),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.FetchStatusRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(net.synchthia.api.nebula.NebulaProtos.FetchStatusResponse.getDefaultInstance()));

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
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
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
     *-
     * MISC
     *-
     * </pre>
     */
    public void ping(net.synchthia.api.nebula.NebulaProtos.Empty request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PING, responseObserver);
    }

    /**
     * <pre>
     *-
     * STREAM
     *-
     * API -&gt; Bungee (Notify Server Status)
     * </pre>
     */
    public void entryStream(net.synchthia.api.nebula.NebulaProtos.StreamRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.EntryStreamResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ENTRY_STREAM, responseObserver);
    }

    /**
     */
    public void quitStream(net.synchthia.api.nebula.NebulaProtos.QuitStreamRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_QUIT_STREAM, responseObserver);
    }

    /**
     * <pre>
     *-
     * ServerEntry
     *-
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

    /**
     * <pre>
     *-
     * ServerStatus
     *-
     * Bungee -&gt; API (Server Status)
     * </pre>
     */
    public void pushStatus(net.synchthia.api.nebula.NebulaProtos.PushStatusRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.PushStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PUSH_STATUS, responseObserver);
    }

    /**
     * <pre>
     * Spigot &lt;- API (Server Status)
     * </pre>
     */
    public void fetchStatus(net.synchthia.api.nebula.NebulaProtos.FetchStatusRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.FetchStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_FETCH_STATUS, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_PING,
            asyncUnaryCall(
              new MethodHandlers<
                net.synchthia.api.nebula.NebulaProtos.Empty,
                net.synchthia.api.nebula.NebulaProtos.Empty>(
                  this, METHODID_PING)))
          .addMethod(
            METHOD_ENTRY_STREAM,
            asyncServerStreamingCall(
              new MethodHandlers<
                net.synchthia.api.nebula.NebulaProtos.StreamRequest,
                net.synchthia.api.nebula.NebulaProtos.EntryStreamResponse>(
                  this, METHODID_ENTRY_STREAM)))
          .addMethod(
            METHOD_QUIT_STREAM,
            asyncUnaryCall(
              new MethodHandlers<
                net.synchthia.api.nebula.NebulaProtos.QuitStreamRequest,
                net.synchthia.api.nebula.NebulaProtos.Empty>(
                  this, METHODID_QUIT_STREAM)))
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
          .addMethod(
            METHOD_PUSH_STATUS,
            asyncUnaryCall(
              new MethodHandlers<
                net.synchthia.api.nebula.NebulaProtos.PushStatusRequest,
                net.synchthia.api.nebula.NebulaProtos.PushStatusResponse>(
                  this, METHODID_PUSH_STATUS)))
          .addMethod(
            METHOD_FETCH_STATUS,
            asyncUnaryCall(
              new MethodHandlers<
                net.synchthia.api.nebula.NebulaProtos.FetchStatusRequest,
                net.synchthia.api.nebula.NebulaProtos.FetchStatusResponse>(
                  this, METHODID_FETCH_STATUS)))
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
     *-
     * MISC
     *-
     * </pre>
     */
    public void ping(net.synchthia.api.nebula.NebulaProtos.Empty request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PING, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *-
     * STREAM
     *-
     * API -&gt; Bungee (Notify Server Status)
     * </pre>
     */
    public void entryStream(net.synchthia.api.nebula.NebulaProtos.StreamRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.EntryStreamResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_ENTRY_STREAM, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void quitStream(net.synchthia.api.nebula.NebulaProtos.QuitStreamRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_QUIT_STREAM, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *-
     * ServerEntry
     *-
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

    /**
     * <pre>
     *-
     * ServerStatus
     *-
     * Bungee -&gt; API (Server Status)
     * </pre>
     */
    public void pushStatus(net.synchthia.api.nebula.NebulaProtos.PushStatusRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.PushStatusResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PUSH_STATUS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Spigot &lt;- API (Server Status)
     * </pre>
     */
    public void fetchStatus(net.synchthia.api.nebula.NebulaProtos.FetchStatusRequest request,
        io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.FetchStatusResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_FETCH_STATUS, getCallOptions()), request, responseObserver);
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
     *-
     * MISC
     *-
     * </pre>
     */
    public net.synchthia.api.nebula.NebulaProtos.Empty ping(net.synchthia.api.nebula.NebulaProtos.Empty request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PING, getCallOptions(), request);
    }

    /**
     * <pre>
     *-
     * STREAM
     *-
     * API -&gt; Bungee (Notify Server Status)
     * </pre>
     */
    public java.util.Iterator<net.synchthia.api.nebula.NebulaProtos.EntryStreamResponse> entryStream(
        net.synchthia.api.nebula.NebulaProtos.StreamRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_ENTRY_STREAM, getCallOptions(), request);
    }

    /**
     */
    public net.synchthia.api.nebula.NebulaProtos.Empty quitStream(net.synchthia.api.nebula.NebulaProtos.QuitStreamRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_QUIT_STREAM, getCallOptions(), request);
    }

    /**
     * <pre>
     *-
     * ServerEntry
     *-
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

    /**
     * <pre>
     *-
     * ServerStatus
     *-
     * Bungee -&gt; API (Server Status)
     * </pre>
     */
    public net.synchthia.api.nebula.NebulaProtos.PushStatusResponse pushStatus(net.synchthia.api.nebula.NebulaProtos.PushStatusRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PUSH_STATUS, getCallOptions(), request);
    }

    /**
     * <pre>
     * Spigot &lt;- API (Server Status)
     * </pre>
     */
    public net.synchthia.api.nebula.NebulaProtos.FetchStatusResponse fetchStatus(net.synchthia.api.nebula.NebulaProtos.FetchStatusRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_FETCH_STATUS, getCallOptions(), request);
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
     *-
     * MISC
     *-
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<net.synchthia.api.nebula.NebulaProtos.Empty> ping(
        net.synchthia.api.nebula.NebulaProtos.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PING, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<net.synchthia.api.nebula.NebulaProtos.Empty> quitStream(
        net.synchthia.api.nebula.NebulaProtos.QuitStreamRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_QUIT_STREAM, getCallOptions()), request);
    }

    /**
     * <pre>
     *-
     * ServerEntry
     *-
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

    /**
     * <pre>
     *-
     * ServerStatus
     *-
     * Bungee -&gt; API (Server Status)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<net.synchthia.api.nebula.NebulaProtos.PushStatusResponse> pushStatus(
        net.synchthia.api.nebula.NebulaProtos.PushStatusRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PUSH_STATUS, getCallOptions()), request);
    }

    /**
     * <pre>
     * Spigot &lt;- API (Server Status)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<net.synchthia.api.nebula.NebulaProtos.FetchStatusResponse> fetchStatus(
        net.synchthia.api.nebula.NebulaProtos.FetchStatusRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_FETCH_STATUS, getCallOptions()), request);
    }
  }

  private static final int METHODID_PING = 0;
  private static final int METHODID_ENTRY_STREAM = 1;
  private static final int METHODID_QUIT_STREAM = 2;
  private static final int METHODID_GET_SERVER_ENTRY = 3;
  private static final int METHODID_ADD_SERVER_ENTRY = 4;
  private static final int METHODID_REMOVE_SERVER_ENTRY = 5;
  private static final int METHODID_PUSH_STATUS = 6;
  private static final int METHODID_FETCH_STATUS = 7;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final NebulaImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(NebulaImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PING:
          serviceImpl.ping((net.synchthia.api.nebula.NebulaProtos.Empty) request,
              (io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.Empty>) responseObserver);
          break;
        case METHODID_ENTRY_STREAM:
          serviceImpl.entryStream((net.synchthia.api.nebula.NebulaProtos.StreamRequest) request,
              (io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.EntryStreamResponse>) responseObserver);
          break;
        case METHODID_QUIT_STREAM:
          serviceImpl.quitStream((net.synchthia.api.nebula.NebulaProtos.QuitStreamRequest) request,
              (io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.Empty>) responseObserver);
          break;
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
        case METHODID_PUSH_STATUS:
          serviceImpl.pushStatus((net.synchthia.api.nebula.NebulaProtos.PushStatusRequest) request,
              (io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.PushStatusResponse>) responseObserver);
          break;
        case METHODID_FETCH_STATUS:
          serviceImpl.fetchStatus((net.synchthia.api.nebula.NebulaProtos.FetchStatusRequest) request,
              (io.grpc.stub.StreamObserver<net.synchthia.api.nebula.NebulaProtos.FetchStatusResponse>) responseObserver);
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

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_PING,
        METHOD_ENTRY_STREAM,
        METHOD_QUIT_STREAM,
        METHOD_GET_SERVER_ENTRY,
        METHOD_ADD_SERVER_ENTRY,
        METHOD_REMOVE_SERVER_ENTRY,
        METHOD_PUSH_STATUS,
        METHOD_FETCH_STATUS);
  }

}
