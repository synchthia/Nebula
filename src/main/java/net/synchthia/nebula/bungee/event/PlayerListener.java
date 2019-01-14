package net.synchthia.nebula.bungee.event;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import net.synchthia.nebula.bungee.NebulaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Laica-Lunasys
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onLogin(LoginEvent event) {
        ServerInfo lobby = NebulaPlugin.getPlugin().serverAPI.determinateLobby();
        if (lobby == null) {
            TextComponent disconnect_prefix = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&f- &b&lSTARTAIL&f -\n\n&f"));
            TextComponent msgEn = new TextComponent(ChatColor.RED + "Login Failed: Couldn't find available Lobby.\n");
            TextComponent msgJa = new TextComponent(ChatColor.RED + "ログインに失敗しました: 接続可能なロビーがありません。\n");
            TextComponent error = new TextComponent(ChatColor.DARK_GRAY + "\n[ERR_LOGIN_EVENT]");

            event.getConnection().disconnect(disconnect_prefix, msgEn, msgJa, error);

            ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Couldn't pass event: LoginEvent (api server is down?)");
        } else {
            event.getConnection().getListener().getServerPriority().clear();
            event.getConnection().getListener().getServerPriority().add(lobby.getName());
        }
    }

    @EventHandler
    public void onServerJoin(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

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

        // Determinate & Get Lobby Server or Disconnect
        ServerInfo lobby = NebulaPlugin.getPlugin().serverAPI.determinateLobby();
        if (lobby != null) {
            event.setCancelServer(lobby);
        } else {
            event.getPlayer().getPendingConnection().disconnect(disconnect_prefix, reason);
        }

        // When couldn't connect to or kicked from lobby server
        if (event.getPlayer().getServer() == null) {
            event.getPlayer().getPendingConnection().disconnect(disconnect_prefix, reason);
            return;
        }

        // Player in Kicked Server (Should be Move Server)
        if (event.getPlayer().getServer().getInfo() == kickedFrom) {
            event.getPlayer().sendMessage(kicked_prefix, reason);

            // Player in Fallback Server (Should be Kick Network)
            if (event.getPlayer().getServer().getInfo() == event.getCancelServer()) {
                // Kick Server
                event.getPlayer().getPendingConnection().disconnect(disconnect_prefix, reason);
            } else {
                // Move Server
                event.setCancelled(true);
            }
        }

        logger.info("[" + event.getPlayer().getName() + "] " + "Kicked from " + kickedFrom.getName() + ": " + reason.toPlainText());
    }
}
