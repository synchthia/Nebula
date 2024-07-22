package net.synchthia.nebula.bukkit.command;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.i18n.I18n;
import net.synchthia.nebula.bukkit.messages.Message;
import net.synchthia.nebula.bukkit.messages.ServerMessage;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

import java.util.Comparator;
import java.util.stream.Stream;

/**
 * @author Laica-Lunasys
 */
@RequiredArgsConstructor
public class ListServersCommand {
    private final NebulaPlugin plugin;

    @Command("servers|glist|listserver|listservers")
    @CommandDescription("Show all servers")
    @Permission("nebula.command.servers")
    public void onServers(final CommandSender sender) {
        Stream<NebulaProtos.ServerEntry> sorted = plugin.getServerAPI().getServers().stream().sorted(
                Comparator.comparing(NebulaProtos.ServerEntry::getName)
        ).sorted(
                Comparator.comparing(NebulaProtos.ServerEntry::getFallback).reversed()
        );

        int total = plugin.getServerAPI().getServers().stream().filter(e -> e.getStatus().getOnline()).mapToInt(e -> e.getStatus().getPlayers().getOnline()).sum();
        I18n.sendMessage(sender, "servers.header", Placeholder.unparsed("_players_", String.valueOf(total)));

        sorted.forEach((server) -> {
            String serverFormat = "<hover:show_text:'<server_lore>'><click:run_command:'/nebula:server <server_id>'><gold><b>[<server_name>]: </b></gold></click></hover>";
            String playerInfo;
            if (server.getStatus().getOnline()) {
                if (server.getStatus().getPlayers().getMax() == 0) {
                    playerInfo = "<dark_gray><b>Starting...</b></dark_gray>";
                } else {
                    playerInfo = "<gray><server_online_players>/<server_max_players></gray>";
                }
            } else {
                playerInfo = "<red><b>Offline</b></red>";
            }

            Component message = Message.create(serverFormat + playerInfo,
                    Placeholder.component("server_lore", ServerMessage.getServerEntryLore(sender, server)),
                    TagResolver.resolver(ServerMessage.getServerEntryResolver(server))
            );

            sender.sendMessage(message);
        });

        sender.sendRichMessage("<dark_gray><b><strikethrough>---------------------------------------------</strikethrough></b></dark_gray>");
    }
}
