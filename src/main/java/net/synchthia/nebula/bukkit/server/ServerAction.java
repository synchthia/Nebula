package net.synchthia.nebula.bukkit.server;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.messages.Message;
import net.synchthia.nebula.bukkit.messages.ServerMessage;
import net.synchthia.nebula.bukkit.util.BungeeUtil;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

/**
 * @author Laica-Lunasys
 */
public class ServerAction {
    public static void connect(NebulaPlugin plugin, Player player, String serverId) {
        Optional<NebulaProtos.ServerEntry> server = plugin.getServerAPI().getServer(serverId);

        if (server.isEmpty()) {
            player.sendMessage(Message.create("<red><server_name> is not found!</red>", Placeholder.unparsed("server_name", serverId)));
            return;
        }

        if (server.get().getName().equals(NebulaPlugin.getServerId())) {
            player.sendMessage(Message.create("<red>Already connected to this server!</red>"));
            return;
        }

        List<TagResolver> resolvers = ServerMessage.getServerEntryResolver(server.get());

        if (server.get().getStatus().getOnline()) {
            player.sendMessage(Message.create("<green>Connecting to <server_name>...</green>", TagResolver.resolver(resolvers)));
            BungeeUtil.connect(player, serverId);
        } else {
            player.sendMessage(Message.create("<red><server_name> is offline!</red>", TagResolver.resolver(resolvers)));
        }
    }
}
