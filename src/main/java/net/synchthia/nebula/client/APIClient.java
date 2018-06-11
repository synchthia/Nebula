package net.synchthia.nebula.client;

import com.google.protobuf.util.JsonFormat;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.synchthia.api.nebula.NebulaGrpc;
import net.synchthia.api.nebula.libs.grpc.ManagedChannel;
import net.synchthia.api.nebula.libs.grpc.internal.DnsNameResolverProvider;
import net.synchthia.api.nebula.libs.grpc.netty.NettyChannelBuilder;
import net.synchthia.api.nebula.libs.grpc.stub.StreamObserver;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static net.synchthia.api.nebula.NebulaProtos.*;

/**
 * @author Laica-Lunasys
 */
public class APIClient {
    private final ManagedChannel channel;
    private final NebulaGrpc.NebulaStub stub;

    public APIClient(@NonNull String target) {
        channel = NettyChannelBuilder.forTarget(target).usePlaintext().nameResolverFactory(new DnsNameResolverProvider()).build();
        stub = NebulaGrpc.newStub(channel);
    }

    // Utility Method
    public static ServerEntryStream entryStreamFromJson(String jsonText) {
        try {
            ServerEntryStream.Builder builder = ServerEntryStream.newBuilder();
            JsonFormat.parser().ignoringUnknownFields().merge(jsonText, builder);

            return builder.build();
        } catch (Exception e) {
            return null;
        }
    }

    public static ServerEntry entryFromJson(String jsonText) {
        try {
            ServerEntry.Builder builder = ServerEntry.newBuilder();
            JsonFormat.parser().ignoringUnknownFields().merge(jsonText, builder);

            return builder.build();
        } catch (Exception e) {
            return null;
        }
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public CompletableFuture<GetServerEntryResponse> getServerEntry() {
        GetServerEntryRequest request = GetServerEntryRequest.newBuilder()
                .build();

        CompletableFuture<GetServerEntryResponse> future = new CompletableFuture<>();
        stub.getServerEntry(request, new CompletableFutureObserver<>(future));
        return future;
    }

    @RequiredArgsConstructor
    private static class CompletableFutureObserver<V> implements StreamObserver<V> {
        private final CompletableFuture<V> future;

        @Override
        public void onNext(V v) {
            future.complete(v);
        }

        @Override
        public void onError(Throwable throwable) {
            future.completeExceptionally(throwable);
        }

        @Override
        public void onCompleted() {
        }
    }
}
