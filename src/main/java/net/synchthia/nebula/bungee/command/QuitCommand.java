package net.synchthia.nebula.bungee.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.synchthia.nebula.bungee.NebulaPlugin;

/**
 * @author Laica-Lunasys
 */
public class QuitCommand extends Command {
    public QuitCommand() {
        super("quit");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder(ChatColor.RED + "Invalid Sender!").create());
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        ServerInfo lobby = NebulaPlugin.getPlugin().serverAPI.determinateLobby();
        if (lobby == null) {
            player.disconnect(new ComponentBuilder(ChatColor.RED + "Disconnected from STARTAIL").create());
            return;
        }

        if (lobby.getName().contains(player.getServer().getInfo().getName())) {
            sender.sendMessage(new ComponentBuilder(ChatColor.RED + "Already Connected to Lobby!").create());
        } else {
            player.connect(lobby);
        }
    }
}
