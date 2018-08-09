package net.synchthia.nebula.bungee.event;

import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.synchthia.nebula.bungee.NebulaPlugin;

import java.awt.*;

/**
 * @author Laica-Lunasys
 */
public class PingListener implements Listener {

    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        ServerPing ping = event.getResponse();
        Integer globalOnline = NebulaPlugin.plugin.serverAPI.getGlobalOnline();
        Integer maxOnline = ping.getPlayers().getMax();
        BaseComponent motd = NebulaPlugin.plugin.proxyAPI.getMotd();
        Favicon favicon = NebulaPlugin.plugin.proxyAPI.getFavicon();

        event.setResponse(new ServerPing(ping.getVersion(), new ServerPing.Players(maxOnline, globalOnline, null), motd, favicon));
    }
}
