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

    public ServerAPI(NebulaPlugin plugin) {
        this.plugin = plugin;
    }

    public void putServer(NebulaProtos.ServerEntry entry) {
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

        //todo: fix it... -> want sort
        ServerSynchronizer.putToLocalServer(serverInfo);
        //ProxyServer.getInstance().getServers().put(entry.getName(), serverInfo.get(entry.getName()));

        System.out.println(entry.getName());
    }

    public void removeServer(String serverName) {
        System.out.println("Removing...: " + serverName);
        ProxyServer.getInstance().getServers().remove(serverName);
    }


    // older
    public CompletableFuture<NebulaProtos.GetServerEntryResponse> getServers() {
        return plugin.apiClient.getServerEntry().whenComplete((servers, throwable) -> {
            Map<String, ServerInfo> serverInfo = new HashMap<>();

            if (throwable != null) {
                plugin.getLogger().log(Level.WARNING, "Failed GetServers: Exception threw", throwable);
            }


            for (NebulaProtos.ServerEntry entry : servers.getEntryList()) {
                serverInfo.put(entry.getName(),
                        ProxyServer.getInstance().constructServerInfo(
                                entry.getName(),
                                new InetSocketAddress(entry.getAddress(), entry.getPort()),
                                entry.getMotd(),
                                false
                        )
                );
            }

            ServerSynchronizer.putToLocalServer(serverInfo);
        });
    }
}
