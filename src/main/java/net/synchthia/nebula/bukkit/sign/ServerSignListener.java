package net.synchthia.nebula.bukkit.sign;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bukkit.server.ServerAPI;
import net.synchthia.nebula.bukkit.server.ServerAction;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

/**
 * @author misterT2525
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ServerSignListener implements Listener {

    private final ServerSignManager manager;
    private final String prefix = "[nebula]";

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onCreateSign(SignChangeEvent event) {
        if (!event.getPlayer().hasPermission("nebula.sign.manage") || !event.getLine(0).equalsIgnoreCase(prefix)) {
            return;
        }

        String key = event.getLine(1);
        if (key.length() == 0) {
            return;
        }

        HashMap<Integer, String> format = manager.getFormat(key);

        event.setLine(0, format.get(0));
        event.setLine(1, format.get(1));
        event.setLine(2, format.get(2));
        event.setLine(3, format.get(3));

        SignData sign = new SignData(event.getBlock(), key);
        sign.updateLines(prefix, sign.getKey(), "?????", "?????");
        manager.getSignManager().add(sign);
        event.getPlayer().sendMessage(ChatColor.GREEN + "Sign Added: " + sign.getKey());
    }

    @EventHandler
    public void onDestroySign(BlockBreakEvent event) {
        if (!event.getPlayer().hasPermission("nebula.sign.manage") || !(event.getBlock().getState() instanceof Sign)) {
            return;
        }

        if (!event.getPlayer().isSneaking()) {
            return;
        }

        manager.getSignManager().findSign(event.getBlock()).ifPresent(signData -> {
            NebulaProtos.ServerEntry server = ServerAPI.getServers().get(signData.getKey());
            manager.getSignManager().removeSign(event.getBlock());

            if (server != null) {
                event.getPlayer().sendMessage(ChatColor.RED + "Sign Removed: " + server.getDisplayName());
            } else {
                event.getPlayer().sendMessage(ChatColor.RED + "Sign Removed: " + signData.getKey());
            }
        });
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (!event.hasBlock()) {
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getPlayer().getGameMode() == GameMode.CREATIVE && event.getPlayer().isSneaking()) {
            return;
        }

        manager.getSignManager().findSign(event.getClickedBlock()).ifPresent(sign -> {
            event.setCancelled(true);
            ServerAction.connect(event.getPlayer(), sign.getKey());
        });
    }
}
