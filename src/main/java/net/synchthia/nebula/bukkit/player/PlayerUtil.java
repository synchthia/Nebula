package net.synchthia.nebula.bukkit.player;

import org.bukkit.entity.Player;

public class PlayerUtil {
    public static boolean isPlayerVanished(Player player) {
        boolean vanished = false;
        if (player.hasMetadata("vanished")) {
            vanished = player.getMetadata("vanished").getFirst().asBoolean();
        }

        return vanished;
    }
}
