package net.synchthia.nebula.bukkit.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import net.synchthia.nebula.bukkit.server.ServerAPI;
import net.synchthia.nebula.bukkit.server.ServerAction;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * @author Laica-Lunasys
 */
public class LobbyCommand extends BaseCommand {
    @CommandAlias("lobby|hub")
    @CommandPermission("nebula.command.lobby")
    public void onLobby(CommandSender sender) {
        ServerAction.connect(Bukkit.getPlayer(sender.getName()), ServerAPI.determinateLobby().getValue().getName());
    }
}
