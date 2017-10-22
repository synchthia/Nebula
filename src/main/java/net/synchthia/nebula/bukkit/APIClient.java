package net.synchthia.nebula.bukkit;

import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.internal.DnsNameResolverProvider;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.synchthia.api.nebula.NebulaGrpc;
import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bukkit.server.ServerAPI;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static net.synchthia.api.nebula.NebulaProtos.*;

/**
 * @author Laica-Lunasys
 */
public class APIClient {
    private final ManagedChannel channel;
    private final NebulaGrpc.NebulaStub stub;
    private final NebulaGrpc.NebulaBlockingStub blockingStub;

    public APIClient(@NonNull String target) {
        channel = NettyChannelBuilder.forTarget(target).usePlaintext(true).nameResolverFactory(new DnsNameResolverProvider()).build();
        stub = NebulaGrpc.newStub(channel);
        blockingStub = NebulaGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        quitStream(Bukkit.getServer().getServerName());
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }


    //---
    // STREAM
    //---
    public void ping() {
        Empty request = Empty.newBuilder().build();

        stub.ping(request, new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty value) {
                NebulaPlugin.getPlugin().getLogger().log(Level.INFO, "### Welcome Back!");
                NebulaPlugin.getPlugin().registerStream();
            }

            @SneakyThrows
            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);

                if (status.getCode().toStatus() == Status.UNAVAILABLE) {
                    NebulaPlugin.getPlugin().getLogger().log(Level.SEVERE, "StatusRuntimeException Occurred! Retrying...");
                    Thread.sleep(3000L);
                    ping();
                    return;
                }

                if (status.getCause() instanceof IOException) {
                    NebulaPlugin.getPlugin().getLogger().log(Level.SEVERE, "IOException Occurred! API Server down?");
                    ping();
                    return;
                }

                System.out.println("Trace ===");
                System.out.println(status.getCause());
                System.out.println(status.getCode());
                System.out.println(status.getDescription());
                t.printStackTrace();
                System.out.println("Trace ===");
            }

            @Override
            public void onCompleted() {

            }
        });
    }

    public void entryStream(@NonNull String name) {
        StreamRequest request = StreamRequest.newBuilder()
                .setType(StreamType.CONNECT)
                .setName(name)
                .build();

        stub.entryStream(request, new StreamObserver<EntryStreamResponse>() {
            @Override
            public void onNext(EntryStreamResponse value) {
                switch (value.getType()) {
                    case RESTORED:
                        NebulaPlugin.getPlugin().getLogger().log(Level.INFO, "### Connected!");
                        break;

                    case DISPATCH:
                        NebulaPlugin.getPlugin().getLogger().log(Level.INFO, "Incoming Request");
                        break;

                    case SYNC:
                        NebulaPlugin.getPlugin().getLogger().log(Level.INFO, "Sync...");
                        //NebulaPlugin.plugin.serverAPI.getServers();
                        ServerAPI.putServer(value.getEntry());
                        NebulaPlugin.getPlugin().getServerSignManager().updateSigns();
                        break;

                    case REMOVE:
                        ServerAPI.removeServer(value.getEntry().getName());
                        NebulaPlugin.getPlugin().getServerSignManager().updateSigns();
                }
            }

            @Override
            public void onError(Throwable t) {
                ping();
            }

            @Override
            public void onCompleted() {
                NebulaPlugin.getPlugin().getLogger().log(Level.INFO, "EntryStream connection closed!");
            }
        });
    }

    public CompletableFuture<NebulaProtos.GetServerEntryResponse> getServerEntry() {
        GetServerEntryRequest request = GetServerEntryRequest.newBuilder().build();

        CompletableFuture<NebulaProtos.GetServerEntryResponse> future = new CompletableFuture<>();
        stub.getServerEntry(request, new APIClient.CompletableFutureObserver<>(future));
        return future;
    }


    public void quitStream(@NonNull String name) {
        QuitEntryStreamRequest request = QuitEntryStreamRequest.newBuilder()
                .setName(name)
                .build();

        blockingStub.quitEntryStream(request);
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
