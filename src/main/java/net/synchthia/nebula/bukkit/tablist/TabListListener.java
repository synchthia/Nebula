package net.synchthia.nebula.bukkit.tablist;

import lombok.RequiredArgsConstructor;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class TabListListener implements Listener {
    private final NebulaPlugin plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getTabList().syncTabList(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getTabList().desyncTabList(event.getPlayer());
    }
}
