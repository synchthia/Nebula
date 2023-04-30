package net.synchthia.nebula.bukkit.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.server.ServerAPI;
import net.synchthia.nebula.bukkit.server.ServerAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * @author Laica-Lunasys
 */
public class LobbyCommand extends BaseCommand {
    @CommandAlias("lobby|hub")
    @CommandPermission("nebula.command.lobby")
    @Description("Back to Lobby")
    public void onLobby(CommandSender sender) {
        Map.Entry<String, NebulaProtos.ServerEntry> lobby = ServerAPI.determinateLobby();
        if (lobby == null) {
            sender.sendMessage(ChatColor.RED + "All Fallback Server is down! Type /quit for Disconnect.");
        } else {
            ServerAction.connect(Bukkit.getPlayer(sender.getName()), lobby.getValue().getName());
        }
    }
}
