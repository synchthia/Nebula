package net.synchthia.nebula.bungee.event;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bungee.NebulaPlugin;

/**
 * @author Laica-Lunasys
 */
public class PingListener implements Listener {

    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        ServerPing ping = event.getResponse();
        Integer globalOnline = 0;
        Integer maxOnline = ping.getPlayers().getMax();
        for (NebulaProtos.ServerStatus status : NebulaPlugin.plugin.serverAPI.getServerStatus().values()) {
            globalOnline = globalOnline + status.getPlayers().getOnline();
        }

        event.setResponse(new ServerPing(ping.getVersion(), new ServerPing.Players(maxOnline, globalOnline, null), ping.getDescriptionComponent(), ping.getFaviconObject()));
    }
}
