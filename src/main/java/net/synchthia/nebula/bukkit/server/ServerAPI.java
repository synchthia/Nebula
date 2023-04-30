package net.synchthia.nebula.bukkit.server;

import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Laica-Lunasys
 */
public class ServerAPI {
    private static Map<String, NebulaProtos.ServerEntry> serverEntry = new HashMap<>();
    private final NebulaPlugin plugin;

    public ServerAPI(NebulaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void putServer(NebulaProtos.ServerEntry entry) {
        serverEntry.put(entry.getName(), entry);
    }

    public static void removeServer(String name) {
        serverEntry.remove(name);
    }

    public static Map.Entry<String, NebulaProtos.ServerEntry> determinateLobby() {
        List<Map.Entry<String, NebulaProtos.ServerEntry>> s = serverEntry.entrySet().stream()
                .filter(e -> e.getValue().getFallback())
                .filter(e -> e.getValue().getStatus().getOnline())
                .sorted(Comparator.comparingInt(e -> e.getValue().getStatus().getPlayers().getOnline()))
                .collect(Collectors.toList());

        if (s.size() == 0) {
            return null;
        } else {
            return s.get(0);
        }
    }

    public static Map<String, NebulaProtos.ServerEntry> getServerEntry() {
        return serverEntry;
    }
}
