package net.synchthia.nebula.bukkit.player;

import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.server.ServerAPI;
import net.synchthia.nebula.bukkit.util.StringUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.logging.Level;

public class PlayerListener implements Listener {
    private final NebulaPlugin plugin;

    public PlayerListener(NebulaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {
        NebulaProtos.ServerEntry serverEntry = ServerAPI.getServerEntry().get(NebulaPlugin.getServerId());
        if (serverEntry == null) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, StringUtil.coloring("&cInternal Server Error (Unknown Server ID)"));
            plugin.getLogger().log(Level.SEVERE, "Couldn't find server as SERVER_ID : " + NebulaPlugin.getServerId());
            return;
        }

        NebulaProtos.Lockdown lockdown = serverEntry.getLockdown();
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
