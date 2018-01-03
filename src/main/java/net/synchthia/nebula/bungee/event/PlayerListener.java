package net.synchthia.nebula.bungee.event;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bungee.NebulaPlugin;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Laica-Lunasys
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onServerJoin(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        ServerInfo lobby = NebulaPlugin.getPlugin().serverAPI.determinateLobby();
        if (lobby != null && event.getPlayer().getServer() == null) {
            event.setTarget(lobby);
        }

        if (!event.getTarget().canAccess(player)) {
            player.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to access this server.")).create());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerKick(ServerKickEvent event) {
        Logger logger = NebulaPlugin.plugin.getLogger();
        ServerInfo kickedFrom = event.getKickedFrom();

        TextComponent disconnect_prefix = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&f- &b&lSTARTAIL&f -\n\n&f"));
        TextComponent kicked_prefix = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&l" + kickedFrom.getName() + "&7≫&r "));
        TextComponent reason = new TextComponent(event.getKickReasonComponent());

        ServerInfo lobby = NebulaPlugin.getPlugin().serverAPI.determinateLobby();
        if (lobby != null) {
            event.setCancelServer(lobby);
        }

        // When couldn't connect to lobby server
        if (event.getPlayer().getServer() == null) {
            event.getPlayer().getPendingConnection().disconnect(disconnect_prefix, reason);
            return;
        }

        // Player in Kicked Server (Should be Move Server)
        if (event.getPlayer().getServer().getInfo() == kickedFrom) {
            event.getPlayer().sendMessage(kicked_prefix, reason);
            event.setCancelled(true);
            return;
        }

        // Player in Fallback Server (Should be Kick Network)
        if (event.getPlayer().getServer().getInfo() == event.getCancelServer()) {
            event.getPlayer().getPendingConnection().disconnect(disconnect_prefix, reason);
            return;
        }

        logger.info("[" + event.getPlayer().getName() + "] " + "Kicked from " + kickedFrom.getName() + ": " + reason.toPlainText());
    }
}
