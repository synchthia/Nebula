package net.synchthia.nebula.velocity;

import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.processing.CommandContainer;
import org.incendo.cloud.annotations.suggestion.Suggestions;
import org.incendo.cloud.context.CommandContext;

import java.util.stream.Stream;

@CommandContainer(priority = 2)
public class CommandSuggestions {
    private final NebulaPlugin plugin;

    public CommandSuggestions(NebulaPlugin plugin) {
        this.plugin = plugin;
    }

    @Suggestions("servers")
    public Stream<String> servers(
            final CommandContext<CommandSender> context,
            final String input
    ) {
        return plugin.getServerAPI().getServers(context.sender()).stream().map(NebulaProtos.ServerEntry::getName);
    }
}
