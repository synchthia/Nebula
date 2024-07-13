package net.synchthia.nebula.bukkit.stream;

import lombok.RequiredArgsConstructor;
import net.synchthia.nebula.api.APIClient;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.messages.Message;
import redis.clients.jedis.JedisPubSub;

import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
@RequiredArgsConstructor
public class ServersSubs extends JedisPubSub {
    private final NebulaPlugin plugin;

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        NebulaProtos.ServerEntryStream serverStream = APIClient.serverEntryStreamFromJson(message);
        assert serverStream != null;
        switch (serverStream.getType()) {
            case SYNC:
                NebulaProtos.ServerEntry entry = serverStream.getEntry();
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    plugin.getServerAPI().putServer(entry);
                    if (entry.getName().equals(NebulaPlugin.getServerId()) && entry.getLockdown().getEnabled()) {
                        plugin.getServer().getOnlinePlayers().forEach(player -> {
                            if (!player.hasPermission("nebula.server." + entry.getName())) {
                                player.kick(Message.create(entry.getLockdown().getDescription()));
                            }
                        });
                    }
                    plugin.getServerSignManager().updateSigns();
                });
                break;
            case REMOVE:
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    plugin.getServerAPI().removeServer(serverStream.getEntry().getName());
                    plugin.getServerSignManager().updateSigns();
                });
                break;
        }
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        plugin.getLogger().log(Level.INFO, "P Subscribed : " + pattern);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        plugin.getLogger().log(Level.INFO, "P UN Subscribed : " + pattern);
    }
}
