package net.synchthia.nebula.bungee.stream;

import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bungee.NebulaPlugin;
import net.synchthia.nebula.client.APIClient;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class BungeeSubs extends JedisPubSub {
    private static final NebulaPlugin plugin = NebulaPlugin.getPlugin();

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        NebulaProtos.BungeeEntryStream stream = APIClient.bungeeEntryStreamFromJson(message);
        switch (stream.getType()) {
            case SYNC:
                plugin.proxyAPI.setBungeeEntry(stream.getEntry());
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
