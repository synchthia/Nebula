package net.synchthia.nebula.bukkit.server;

import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class ServerAPI {
    private final NebulaPlugin plugin;
    private final List<NebulaProtos.ServerEntry> servers = new ArrayList<>();

    public ServerAPI(NebulaPlugin plugin) {
        this.plugin = plugin;
    }

    public CompletableFuture<NebulaProtos.GetServerEntryResponse> fetch() {
        return plugin.getApiClient().getServerEntry().whenComplete((res, throwable) -> {
            if (throwable != null) {
                plugin.getLogger().log(Level.WARNING, "Failed fetch servers: ", throwable);
                return;
            }

            this.servers.clear();
            this.servers.addAll(res.getEntryList());
        });
    }

    public void putServer(NebulaProtos.ServerEntry entry) {
        this.servers.removeIf(e -> e.getName().equals(entry.getName()));
        this.servers.add(entry);
    }

    public void removeServer(String name) {
        this.servers.removeIf(e -> e.getName().equals(name));
    }

    public NebulaProtos.ServerEntry determinateLobby() {
        Optional<NebulaProtos.ServerEntry> server = this.servers.stream()
                .filter(NebulaProtos.ServerEntry::getFallback)
                .filter(e -> e.getStatus().getOnline())
                .filter(e -> !e.getLockdown().getEnabled())
                .min(Comparator.comparingInt(e -> e.getStatus().getPlayers().getOnline()));

        return server.orElse(null);
    }

    public Optional<NebulaProtos.ServerEntry> getServer(String serverId) {
        return this.servers.stream().filter(server -> server.getName().equals(serverId)).findFirst();
    }

    public List<NebulaProtos.ServerEntry> getServers() {
        return this.servers;
    }

    public List<NebulaProtos.ServerEntry> getServers(CommandSender sender) {
        return this.servers.stream().filter(server -> {
            if (!server.getLockdown().getEnabled()) {
                return true;
            } else {
                return sender.hasPermission("nebula.server." + server.getName());
            }
        }).toList();
    }
}
