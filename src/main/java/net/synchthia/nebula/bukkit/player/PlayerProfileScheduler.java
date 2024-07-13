package net.synchthia.nebula.bukkit.player;

import lombok.RequiredArgsConstructor;
import net.synchthia.nebula.bukkit.NebulaPlugin;

import java.util.logging.Level;

@RequiredArgsConstructor
public class PlayerProfileScheduler {
    private final NebulaPlugin plugin;

    public void run() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (!plugin.getServer().getOnlinePlayers().isEmpty()) {
                plugin.getPlayerAPI().updateAllPlayers(plugin.getServer().getOnlinePlayers()).whenComplete((response, throwable) -> {
                    if (throwable != null) {
                        plugin.getLogger().log(Level.WARNING, "Failed sync player status: ", throwable);
                    }
                });
            }
        }, 0, 20L * 15L);
    }
}
