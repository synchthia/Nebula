package net.synchthia.nebula.bukkit.player;

import io.grpc.Status;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class PlayerAPI {
    private final NebulaPlugin plugin;

    public PlayerAPI(NebulaPlugin plugin) {
        this.plugin = plugin;
    }

    public CompletableFuture<NebulaProtos.IPLookupResponse> lookupIP(String playerIP) {
        return plugin.apiClient.getIPLookup(playerIP).whenComplete((ipLookupResponse, throwable) -> {
            if (throwable != null) {
                if (Objects.equals(Status.fromThrowable(throwable).getDescription(), "context deadline exceeded")) {
                    plugin.getLogger().log(Level.WARNING, "Failed fetch Bungee Entry, retrying...");
                    lookupIP(playerIP).join();
                } else {
                    plugin.getLogger().log(Level.WARNING, "Failed lookup ip" + playerIP);
                }
            }
        });
    }
}
