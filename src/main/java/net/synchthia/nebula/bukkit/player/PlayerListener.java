package net.synchthia.nebula.bukkit.player;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.messages.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerProfile gameProfile = event.getPlayer().getPlayerProfile();

        Set<NebulaProtos.PlayerProperty> properties = new HashSet<>();
        for (ProfileProperty property : gameProfile.getProperties()) {
            properties.add(NebulaProtos.PlayerProperty.newBuilder()
                    .setName(property.getName())
                    .setValue(property.getValue())
                    .setSignature(property.getSignature() != null ? property.getSignature() : "")
                    .build());
        }

        plugin.getPlayerAPI().requestPlayerLogin(NebulaProtos.PlayerProfile.newBuilder()
                .setPlayerUUID(event.getPlayer().getUniqueId().toString())
                .setPlayerName(event.getPlayer().getName())
                .setCurrentServer(NebulaPlugin.getServerId())
                .setPlayerLatency(event.getPlayer().getPing())
                .addAllProperties(properties)
                .setHide(PlayerUtil.isPlayerVanished(event.getPlayer()))
                .build());
    }
}
