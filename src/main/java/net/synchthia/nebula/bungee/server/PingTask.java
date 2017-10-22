package net.synchthia.nebula.bungee.server;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bungee.NebulaPlugin;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class PingTask extends Plugin implements Runnable {
    ScheduledTask tasks;

    @Override
    public void run() {
        /*tasks = ProxyServer.getInstance().getScheduler().schedule(this, () -> {
            if (!BungeeCord.getInstance().isRunning) {
                this.tasks.cancel();
                return;
            }

            for (Map.Entry<String, ServerInfo> entry : ProxyServer.getInstance().getServers().entrySet()) {
                String server = entry.getKey();
                ProxyServer.getInstance().getServerInfo(server).ping((ServerPing cb, Throwable throwable) -> {
                    try {
                        NebulaProtos.ServerStatus status = null;
                        if (cb == null) {
                            status = NebulaProtos.ServerStatus.newBuilder()
                                    .setOnline("offline")
                                    .setOnlinePlayers(0)
                                    .setMaxPlayers(0)
                                    .build();
                            System.out.println("Offline: " + server);
                        } else {
                            String msg = String.format("%s: [%d/%d] (%s)", server, cb.getPlayers().getOnline(), cb.getPlayers().getMax(), cb.getDescriptionComponent().toLegacyText());
                            System.out.println(msg);
                            status = NebulaProtos.ServerStatus.newBuilder()
                                    .setOnline("online")
                                    .setOnlinePlayers(cb.getPlayers().getOnline())
                                    .setMaxPlayers(cb.getPlayers().getMax())
                                    .build();
                        }

                        NebulaPlugin.plugin.apiClient.pushStatus(server, status);
                    } catch (Exception ex) {
                        getLogger().log(Level.WARNING, "Exception threw @ PingTask", ex);
                    }
                });
            }
        }, 1, 1, TimeUnit.SECONDS);*/
    }
}
