package net.synchthia.nebula.bukkit.player;

import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.messages.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Optional;
import java.util.logging.Level;

public class PlayerListener implements Listener {
    private final NebulaPlugin plugin;

    public PlayerListener(NebulaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {
        Optional<NebulaProtos.ServerEntry> server = plugin.getServerAPI().getServer(NebulaPlugin.getServerId());
        if (server.isEmpty()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Message.create("<red>Internal Server Error</red> <gray>(Unknown Server ID)</gray>"));
            plugin.getLogger().log(Level.SEVERE, "Couldn't find server as SERVER_ID : " + NebulaPlugin.getServerId());
            return;
        }

        NebulaProtos.Lockdown lockdown = server.get().getLockdown();
        if (!lockdown.getEnabled()) {
            return;
        }

        if (event.getPlayer().hasPermission("nebula.server." + NebulaPlugin.getServerId())) {
            event.allow();
        } else {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Message.create(lockdown.getDescription()));
        }
    }
}
