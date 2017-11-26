package net.synchthia.nebula.bungee.server;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bungee.NebulaPlugin;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class ServerAPI {
    private final NebulaPlugin plugin;
    private static Map<String, NebulaProtos.ServerStatus> serverStatus = new HashMap<>();

    public ServerAPI(NebulaPlugin plugin) {
        this.plugin = plugin;
    }

    public void putServer(NebulaProtos.ServerEntry entry) {
        serverStatus.put(entry.getName(), entry.getStatus());

        Map<String, ServerInfo> serverInfo = new HashMap<>();
        serverInfo.putAll(ProxyServer.getInstance().getServers());
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
        serverStatus.remove(serverName);
        ProxyServer.getInstance().getServers().remove(serverName);
    }

    public final Map<String, NebulaProtos.ServerStatus> getServerStatus() {
        return serverStatus;
    }

    // older
    public CompletableFuture<NebulaProtos.GetServerEntryResponse> getServers() {
        return plugin.apiClient.getServerEntry().whenComplete((servers, throwable) -> {
            if (throwable != null) {
                plugin.getLogger().log(Level.WARNING, "Failed GetServers: Exception threw", throwable);
            }

            // forEach: entry -> putServer(entry)
            servers.getEntryList().forEach((this::putServer));
        });
    }
}
