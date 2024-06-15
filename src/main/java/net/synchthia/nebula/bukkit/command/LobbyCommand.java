package net.synchthia.nebula.bukkit.command;

import lombok.RequiredArgsConstructor;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.server.ServerAction;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

/**
 * @author Laica-Lunasys
 */
@RequiredArgsConstructor
public class LobbyCommand {
    private final NebulaPlugin plugin;

    @Command("lobby|hub")
    @CommandDescription("Back to Lobby")
    @Permission("nebula.command.lobby")
    public void onLobby(final Player player) {
        NebulaProtos.ServerEntry lobby = plugin.getServerAPI().determinateLobby();
        if (lobby == null) {
            player.sendRichMessage("<red>All Fallback Server is down! Type /quit for Disconnect.</red>");
        } else {
            ServerAction.connect(plugin, player, lobby.getName());
        }
    }
}
