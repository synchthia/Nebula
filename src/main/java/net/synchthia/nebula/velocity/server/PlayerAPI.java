package net.synchthia.nebula.velocity.server;

import io.grpc.Status;
import lombok.RequiredArgsConstructor;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.velocity.NebulaVelocityPlugin;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class PlayerAPI {
    private final NebulaVelocityPlugin plugin;

    public CompletableFuture<NebulaProtos.IPLookupResponse> lookupIP(String playerIP) {
        return plugin.getApiClient().getIPLookup(playerIP).whenComplete(((ipLookupResponse, throwable) -> {
            if (throwable != null) {
                if (Objects.equals(Status.fromThrowable(throwable).getDescription(), "context deadline exceeded")) {
                    plugin.getLogger().warn("Failed fetch Bungee Entry, retrying...");
                    lookupIP(playerIP).join();
                } else {
                    plugin.getLogger().warn("Failed lookup ip" + playerIP);
                }
            }
        }));
    }
}
