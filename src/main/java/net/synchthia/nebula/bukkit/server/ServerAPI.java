package net.synchthia.nebula.bukkit.server;

import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Laica-Lunasys
 */
public class ServerAPI {
    private static Map<String, NebulaProtos.ServerEntry> servers = new HashMap<>();
    private final NebulaPlugin plugin;

    public ServerAPI(NebulaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void putServer(NebulaProtos.ServerEntry entry) {
        servers.put(entry.getName(), entry);
    }

    public static void removeServer(String name) {
        servers.remove(name);
    }

    public static Map<String, NebulaProtos.ServerEntry> getServers() {
        return servers;
    }
}
