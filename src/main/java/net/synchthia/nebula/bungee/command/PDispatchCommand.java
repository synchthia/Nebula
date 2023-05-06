package net.synchthia.nebula.bungee.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.synchthia.nebula.bungee.NebulaPlugin;

public class PDispatchCommand extends Command {
    public PDispatchCommand() {
        super("pdispatch");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("nebula.command.pdispatch")) {
            sender.sendMessage(new ComponentBuilder(ChatColor.RED + "You don't have permission to do this.").create());
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(new ComponentBuilder(ChatColor.RED + "Usage: /pdispatch <command>").create());
            return;
        }

        String cmd = String.join(" ", args);
        NebulaPlugin.getPlugin().apiClient.sendBungeeCommand(cmd).whenComplete((res, throwable) -> {
            if (throwable != null) {
                sender.sendMessage(new ComponentBuilder(ChatColor.RED + "Something went wrong!").create());
                sender.sendMessage(new ComponentBuilder(ChatColor.RED + throwable.getMessage()).create());
                return;
            }
            sender.sendMessage(new ComponentBuilder(ChatColor.GOLD + "Sent proxy command: /" + cmd).create());
        });
    }
}
