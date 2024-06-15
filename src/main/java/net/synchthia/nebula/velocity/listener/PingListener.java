package net.synchthia.nebula.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;
import com.velocitypowered.api.util.Favicon;
import net.kyori.adventure.text.Component;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.velocity.NebulaVelocityPlugin;

import java.util.ArrayList;
import java.util.Optional;

public class PingListener {
    private final NebulaVelocityPlugin plugin;

    public PingListener(NebulaVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onProxyPing(ProxyPingEvent event) {
        ServerPing ping = event.getPing();

        String versionName = "Unknown";
        int protocol = 0;

        Optional<NebulaProtos.ServerEntry> lobby = plugin.getServerAPI().determinateLobby();
        if (lobby.isPresent()) {
            versionName = lobby.get().getStatus().getVersion().getName().replaceAll("Paper ", "").replaceAll("Spigot ", "");
            protocol = lobby.get().getStatus().getVersion().getProtocol();
        }

        int globalOnline = plugin.getServerAPI().getGlobalOnline();
        int maxOnline = 0;
        if (ping.getPlayers().isPresent()) {
            maxOnline = ping.getPlayers().get().getMax();
        }
        Component motd = plugin.getProxyAPI().getMotd();
        Favicon favicon = plugin.getProxyAPI().getFavicon();

        event.setPing(new ServerPing(ping.getVersion(), new ServerPing.Players(globalOnline, maxOnline, new ArrayList<>()), motd, favicon));
    }
}
