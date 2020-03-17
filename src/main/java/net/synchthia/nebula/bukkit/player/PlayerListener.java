package net.synchthia.nebula.bukkit.player;

import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.server.ServerAPI;
import net.synchthia.nebula.bukkit.util.StringUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerListener implements Listener {
    private final NebulaPlugin plugin;

    public PlayerListener(NebulaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {
        NebulaProtos.Lockdown lockdown = ServerAPI.getServerEntry().get(NebulaPlugin.getServerId()).getLockdown();
        if (!lockdown.getEnabled()) {
            return;
        }

        if (event.getPlayer().hasPermission("nebula.server." + NebulaPlugin.getServerId())) {
            event.allow();
        } else {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, StringUtil.coloring(lockdown.getDescription()));
        }
    }
}
