package net.synchthia.nebula.bukkit.command;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.i18n.I18n;
import net.synchthia.nebula.bukkit.messages.Message;
import net.synchthia.nebula.bukkit.server.ServerAction;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

/**
 * @author Laica-Lunasys
 */
@RequiredArgsConstructor
public class ServerCommand {
    private final NebulaPlugin plugin;

    @Command("server|sv [server]")
    @CommandDescription("Switch server")
    @Permission("nebula.command.server")
    public void onLobby(final Player player, @Argument(value = "server", suggestions = "servers") String targetServer) {
        if (targetServer == null) {
            I18n.sendMessage(player, "server.info.current", Placeholder.unparsed("server_name", NebulaPlugin.getServerName()));
            return;
        }

        ServerAction.connect(plugin, player, targetServer);
    }
}
