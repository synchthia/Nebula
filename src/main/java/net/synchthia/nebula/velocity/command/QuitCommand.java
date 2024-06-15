package net.synchthia.nebula.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.velocity.NebulaVelocityPlugin;

import java.util.Optional;

public class QuitCommand implements SimpleCommand {
    private final NebulaVelocityPlugin plugin;

    public QuitCommand(NebulaVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(final Invocation invocation) {
        CommandSource source = invocation.source();
        if (!(source instanceof Player player)) {
            source.sendMessage(MiniMessage.miniMessage().deserialize("<red>Invalid Sender!"));
            return;
        }

        Optional<ServerConnection> currentServer = player.getCurrentServer();
        Optional<NebulaProtos.ServerEntry> lobbyEntry = plugin.getServerAPI().determinateLobby();
        if (lobbyEntry.isPresent()) {
            Optional<RegisteredServer> lobbyServer = plugin.getServer().getServer(lobbyEntry.get().getName());
            if (lobbyServer.isEmpty()) {
                player.disconnect(MiniMessage.miniMessage().deserialize("<red>Disconnected from the server."));
                return;
            }

            if (currentServer.isPresent() && currentServer.get().getServerInfo().getName().equals(lobbyEntry.get().getName())) {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Already connected to Lobby!"));
            } else {
                player.createConnectionRequest(lobbyServer.get());
            }
        } else {
            player.disconnect(MiniMessage.miniMessage().deserialize("Logout."));
        }
    }
}
