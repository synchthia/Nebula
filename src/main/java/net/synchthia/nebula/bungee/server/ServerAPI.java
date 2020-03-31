package net.synchthia.nebula.bungee.server;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bungee.NebulaPlugin;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author Laica-Lunasys
 */
public class ServerAPI {
    private static Map<String, NebulaProtos.ServerEntry> serverEntry = new HashMap<>();
    //private static HashSet<NebulaProtos.ServerEntry> serverEntry = new HashSet<>();
    private final NebulaPlugin plugin;
    //private static Map<String, NebulaProtos.ServerStatus> serverStatus = new HashMap<>();

    public ServerAPI(NebulaPlugin plugin) {
        this.plugin = plugin;
    }

    public void putServer(NebulaProtos.ServerEntry entry) {
        serverEntry.put(entry.getName(), entry);

        Map<String, ServerInfo> serverInfo = new HashMap<>(ProxyServer.getInstance().getServers());
        serverInfo.put(entry.getName(),
                ProxyServer.getInstance().constructServerInfo(
                        entry.getName(),
                        new InetSocketAddress(entry.getAddress(), entry.getPort()),
                        entry.getMotd(),
                        false
                )
        );

        ServerSynchronizer.putToLocalServer(serverInfo);
    }

    public void removeServer(String serverName) {
        serverEntry.remove(serverName);
        ProxyServer.getInstance().getServers().remove(serverName);
    }

    public NebulaProtos.ServerEntry determinateLobby() {
        List<Map.Entry<String, NebulaProtos.ServerEntry>> s = serverEntry.entrySet().stream()
                .filter(e -> e.getValue().getFallback())
                .filter(e -> e.getValue().getStatus().getOnline())
                .sorted(Comparator.comparingInt(e -> e.getValue().getStatus().getPlayers().getOnline()))
                .collect(Collectors.toList());

        if (s.size() == 0) {
            return null;
        } else {
            return s.get(0).getValue();
        }
    }

    public Integer getGlobalOnline() {
        return serverEntry.entrySet().stream()
                .mapToInt(e -> e.getValue().getStatus().getPlayers().getOnline())
                .sum();
    }

    // older
    public CompletableFuture<NebulaProtos.GetServerEntryResponse> getServersFromAPI() {
        return plugin.apiClient.getServerEntry().whenComplete((servers, throwable) -> {
            if (throwable != null) {
                plugin.getLogger().log(Level.WARNING, "Failed GetServers: Exception threw", throwable);
                return;
            }

            // forEach: entry -> putServer(entry)
            servers.getEntryList().forEach(this::putServer);
        });
    }
}
