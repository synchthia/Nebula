package net.synchthia.nebula.bukkit.server;

import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bukkit.util.BungeeUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author Laica-Lunasys
 */
public class ServerAction {
    public static void connect(Player player, String serverName) {
        NebulaProtos.ServerEntry server = ServerAPI.getServers().get(serverName);

        if (server == null) {
            player.sendMessage(ChatColor.RED + serverName + " is not found!");
            return;
        }

        if (server.getStatus().getOnline()) {
            player.sendMessage(ChatColor.GREEN + "Connecting to " + server.getDisplayName() + "...");
            BungeeUtil.connect(player, serverName);
        } else {
            player.sendMessage(ChatColor.RED + server.getDisplayName() + " is Offline!");
        }
    }
}
