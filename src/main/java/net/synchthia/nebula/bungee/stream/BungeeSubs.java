package net.synchthia.nebula.bungee.stream;

import net.synchthia.nebula.api.APIClient;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bungee.NebulaCommandSender;
import net.synchthia.nebula.bungee.NebulaPlugin;
import redis.clients.jedis.JedisPubSub;

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
            case COMMAND:
                plugin.getLogger().log(Level.INFO, "Proxy dispatch received: /" + stream.getCommand());
                plugin.getProxy().getPluginManager().dispatchCommand(NebulaCommandSender.getInstance(), stream.getCommand());
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
