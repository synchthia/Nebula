package net.synchthia.nebula.velocity.server;

import com.velocitypowered.api.proxy.server.ServerInfo;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.velocity.NebulaVelocityPlugin;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ServerAPI {
    private final NebulaVelocityPlugin plugin;
    private final Map<String, NebulaProtos.ServerEntry> serverEntry = new HashMap<>();

    public ServerAPI(NebulaVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    public void putServer(NebulaProtos.ServerEntry entry) {
        serverEntry.put(entry.getName(), entry);

        ServerSynchronizer.putToLocalServer(plugin, entry.getName(), new ServerInfo(
                entry.getName(),
                new InetSocketAddress(entry.getAddress(), entry.getPort())
        ));
    }

    public void removeServer(String serverName) {
        serverEntry.remove(serverName);
        ServerSynchronizer.removeLocalServer(plugin, serverName);
    }

    public Optional<NebulaProtos.ServerEntry> determinateLobby() {
        List<Map.Entry<String, NebulaProtos.ServerEntry>> s = serverEntry.entrySet().stream()
                .filter(e -> e.getValue().getFallback())
                .filter(e -> e.getValue().getStatus().getOnline())
                .sorted(Comparator.comparingInt(e -> e.getValue().getStatus().getPlayers().getOnline()))
                .toList();

        if (s.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(s.get(0).getValue());
        }
    }

    public Integer getGlobalOnline() {
        return serverEntry.entrySet().stream()
                .mapToInt(e -> e.getValue().getStatus().getPlayers().getOnline())
                .sum();
    }

    public NebulaProtos.ServerEntry getServer(String serverName) {
        return this.serverEntry.get(serverName);
    }

    public CompletableFuture<NebulaProtos.GetServerEntryResponse> getServersFromAPI() {
        return plugin.getApiClient().getServerEntry().whenComplete((servers, throwable) -> {
            if (throwable != null) {
                plugin.getLogger().warn("Failed GetServers: Exception threw", throwable);
                return;
            }

            // forEach: entry -> putServer(entry)
            servers.getEntryList().forEach(this::putServer);
        });
    }
}
