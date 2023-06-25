package net.synchthia.nebula.bukkit.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.messages.Message;
import net.synchthia.nebula.bukkit.messages.ServerMessage;
import net.synchthia.nebula.bukkit.server.ServerAction;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.stream.Stream;

/**
 * @author Laica-Lunasys
 */
@RequiredArgsConstructor
public class ServerCommand extends BaseCommand {
    private final NebulaPlugin plugin;

    @CommandAlias("server|sv")
    @CommandCompletion("@servers")
    @Description("Switch server")
    public void onServer(Player player, @Optional String server) {
        if (server == null) {
            player.sendMessage(Message.create("<aqua><b>You are currently on</b></aqua> <blue><b><server_name></b></blue>", Placeholder.unparsed("server_name", NebulaPlugin.getServerName())));
            return;
        }

        ServerAction.connect(plugin, player, server);
    }

    @CommandAlias("servers|glist|listserver|listservers")
    @CommandPermission("nebula.command.servers")
    @Description("Show all servers")
    public void onServers(CommandSender sender) {
        sender.sendRichMessage("<dark_gray><b>[<aqua>Servers</aqua>]<strikethrough>-----------------------------------</strikethrough></b></dark_gray>");
        Stream<NebulaProtos.ServerEntry> sorted = plugin.getServerAPI().getServers().stream().sorted(
                Comparator.comparing(NebulaProtos.ServerEntry::getName)
        ).sorted(
                Comparator.comparing(NebulaProtos.ServerEntry::getFallback).reversed()
        );

        int total = plugin.getServerAPI().getServers().stream().filter(e -> e.getStatus().getOnline()).mapToInt(e -> e.getStatus().getPlayers().getOnline()).sum();
        sender.sendMessage(Message.create("<yellow>Total players online: <players></yellow>", Placeholder.unparsed("players", String.valueOf(total))));

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
