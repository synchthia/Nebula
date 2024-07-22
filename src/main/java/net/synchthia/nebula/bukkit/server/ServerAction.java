package net.synchthia.nebula.bukkit.server;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.i18n.I18n;
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
            I18n.sendMessage(player, "server.error.not_found", Placeholder.unparsed("server_name", serverId));
            return;
        }

        if (server.get().getName().equals(NebulaPlugin.getServerId())) {
            I18n.sendMessage(player, "server.error.already_connected");
            return;
        }

        List<TagResolver> resolvers = ServerMessage.getServerEntryResolver(server.get());

        if (server.get().getStatus().getOnline()) {
            I18n.sendMessage(player, "server.info.connecting", TagResolver.resolver(resolvers));
            BungeeUtil.connect(player, serverId);
        } else {
            I18n.sendMessage(player, "server.error.offline", TagResolver.resolver(resolvers));
        }
    }
}
