package net.synchthia.nebula.bukkit.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.synchthia.nebula.bukkit.server.ServerAPI;
import net.synchthia.nebula.bukkit.server.ServerAction;
import net.synchthia.nebula.bukkit.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * @author Laica-Lunasys
 */
public class ServerCommand extends BaseCommand {

    @CommandAlias("server|sv")
    @CommandCompletion("@servers")
    @Description("")
    public void onServer(CommandSender sender, @Optional String server) {
        if (server == null) {
            sender.sendMessage(StringUtil.coloring("&b&lYou are currently on &9&l" + Bukkit.getServer().getServerName()));
            return;
        }

        ServerAction.connect(Bukkit.getPlayer(sender.getName()), server);
    }

    @CommandAlias("servers|glist|listserver|listservers")
    @CommandPermission("nebula.command.servers")
    public void onServers(CommandSender sender) {
        sender.sendMessage(StringUtil.coloring("&8&m[&b&lServers&8&m]-----------------------------------"));
        ServerAPI.getServerEntry().forEach((k, v) -> {
            String s1 = String.format("&6&l[%s]: ", v.getDisplayName());
            String s2;
            if (v.getStatus().getOnline()) {
                s2 = String.format("&7%d/%d", v.getStatus().getPlayers().getOnline(), v.getStatus().getPlayers().getMax());
            } else {
                s2 = "&c&lOffline";
            }
            sender.sendMessage(StringUtil.coloring(s1 + s2));
        });
        sender.sendMessage(StringUtil.coloring("&8&m----------------------------------------------"));
    }
}
