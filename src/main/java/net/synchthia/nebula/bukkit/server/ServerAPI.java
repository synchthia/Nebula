package net.synchthia.nebula.bukkit.server;

import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;

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

    private static Map<String, NebulaProtos.ServerEntry> servers = new HashMap<>();

    public ServerAPI(NebulaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void putServer(NebulaProtos.ServerEntry entry) {
        servers.put(entry.getName(), entry);

        System.out.println(entry);
    }

    public static void removeServer(String name) {
        servers.remove(name);
    }

    public static Map<String, NebulaProtos.ServerEntry> getServers() {
        return servers;
    }
}
