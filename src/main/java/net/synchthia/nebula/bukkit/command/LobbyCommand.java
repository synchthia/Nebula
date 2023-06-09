package net.synchthia.nebula.bukkit.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import lombok.RequiredArgsConstructor;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.server.ServerAction;
import org.bukkit.entity.Player;

/**
 * @author Laica-Lunasys
 */
@RequiredArgsConstructor
public class LobbyCommand extends BaseCommand {
    private final NebulaPlugin plugin;

    @CommandAlias("lobby|hub")
    @CommandPermission("nebula.command.lobby")
    @Description("Back to Lobby")
    public void onLobby(Player player) {
        NebulaProtos.ServerEntry lobby = plugin.getServerAPI().determinateLobby();
        if (lobby == null) {
            player.sendRichMessage("<red>All Fallback Server is down! Type /quit for Disconnect.</red>");
        } else {
            ServerAction.connect(plugin, player, lobby.getName());
        }
    }
}
