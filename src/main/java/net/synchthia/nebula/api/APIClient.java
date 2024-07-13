package net.synchthia.nebula.api;

import com.google.protobuf.util.JsonFormat;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static net.synchthia.nebula.api.NebulaProtos.*;

/**
 * @author Laica-Lunasys
 */
public class APIClient {
    private final ManagedChannel channel;
    private final NebulaGrpc.NebulaStub stub;

    public APIClient(@NonNull String target) {
        channel = NettyChannelBuilder.forTarget(target).usePlaintext().build();
        stub = NebulaGrpc.newStub(channel);
    }

    // Utility Method
    public static PlayerPropertiesStream playerPropertiesStreamFromJson(String jsonText) {
        try {
            PlayerPropertiesStream.Builder builder = PlayerPropertiesStream.newBuilder();
            JsonFormat.parser().ignoringUnknownFields().merge(jsonText, builder);

            return builder.build();
        } catch (Exception e) {
            return null;
        }
    }

    public static ServerEntryStream serverEntryStreamFromJson(String jsonText) {
        try {
            ServerEntryStream.Builder builder = ServerEntryStream.newBuilder();
            JsonFormat.parser().ignoringUnknownFields().merge(jsonText, builder);

            return builder.build();
        } catch (Exception e) {
            return null;
        }
    }

    public static BungeeEntryStream bungeeEntryStreamFromJson(String jsonText) {
        try {
            BungeeEntryStream.Builder builder = BungeeEntryStream.newBuilder();
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

    public CompletableFuture<GetBungeeEntryResponse> getBungeeEntry() {
        GetBungeeEntryRequest request = GetBungeeEntryRequest.newBuilder()
                .build();

        CompletableFuture<GetBungeeEntryResponse> future = new CompletableFuture<>();
        stub.getBungeeEntry(request, new CompletableFutureObserver<>(future));
        return future;
    }

    public CompletableFuture<SendBungeeCommandResponse> sendBungeeCommand(String command) {
        SendBungeeCommandRequest request = SendBungeeCommandRequest.newBuilder()
                .setCommand(command)
                .build();

        CompletableFuture<SendBungeeCommandResponse> future = new CompletableFuture<>();
        stub.sendBungeeCommand(request, new CompletableFutureObserver<>(future));
        return future;
    }

    public CompletableFuture<IPLookupResponse> getIPLookup(String ipAddress) {
        IPLookupRequest request = IPLookupRequest.newBuilder()
                .setIpAddress(ipAddress)
                .build();

        CompletableFuture<IPLookupResponse> future = new CompletableFuture<>();

        // WTF
        stub.iPLookup(request, new CompletableFutureObserver<>(future));
        return future;
    }

    public CompletableFuture<PlayerLoginResponse> playerLogin(PlayerProfile profile) {
        PlayerLoginRequest request = PlayerLoginRequest.newBuilder()
                .setProfile(profile)
                .build();

        CompletableFuture<PlayerLoginResponse> future = new CompletableFuture<>();
        stub.playerLogin(request, new CompletableFutureObserver<>(future));
        return future;
    }

    public CompletableFuture<PlayerQuitResponse> playerQuit(PlayerProfile profile) {
        PlayerQuitRequest request = PlayerQuitRequest.newBuilder()
                .setProfile(profile)
                .build();

        CompletableFuture<PlayerQuitResponse> future = new CompletableFuture<>();
        stub.playerQuit(request, new CompletableFutureObserver<>(future));
        return future;
    }

    public CompletableFuture<FetchAllPlayersResponse> fetchAllPlayers() {
        FetchAllPlayersRequest request = FetchAllPlayersRequest.newBuilder()
                .build();

        CompletableFuture<FetchAllPlayersResponse> future = new CompletableFuture<>();
        stub.fetchAllPlayers(request, new CompletableFutureObserver<>(future));
        return future;
    }

    public CompletableFuture<UpdateAllPlayersResponse> updateAllPlayers(List<NebulaProtos.PlayerProfile> profiles) {
        UpdateAllPlayersRequest request = UpdateAllPlayersRequest.newBuilder()
                .addAllProfiles(profiles)
                .build();

        CompletableFuture<UpdateAllPlayersResponse> future = new CompletableFuture<>();
        stub.updateAllPlayers(request, new CompletableFutureObserver<>(future));
        return future;
    }

    private record CompletableFutureObserver<V>(CompletableFuture<V> future) implements StreamObserver<V> {
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
