package net.synchthia.nebula.bukkit.command;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.messages.Message;
import net.synchthia.nebula.bukkit.server.ServerAction;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

import java.util.List;

/**
 * @author Laica-Lunasys
 */
@RequiredArgsConstructor
public class ServerCommand {
    private final NebulaPlugin plugin;

    @Command("server|sv [server]")
    @CommandDescription("Switch server")
    @Permission("nebula.command.lobby")
    public void onLobby(final Player player, @Argument(value = "server", suggestions = "servers") String targetServer) {
        if (targetServer == null) {
            player.sendMessage(Message.create("<aqua><b>You are currently on</b></aqua> <blue><b><server_name></b></blue>", Placeholder.unparsed("server_name", NebulaPlugin.getServerName())));
            return;
        }

        ServerAction.connect(plugin, player, targetServer);
    }
}
