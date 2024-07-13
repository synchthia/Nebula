package net.synchthia.nebula.velocity.stream;

import net.synchthia.nebula.api.APIClient;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.velocity.NebulaVelocityPlugin;
import redis.clients.jedis.JedisPubSub;

/**
 * @author Laica-Lunasys
 */
public class BungeeSubs extends JedisPubSub {
    private final NebulaVelocityPlugin plugin;

    public BungeeSubs(NebulaVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        NebulaProtos.BungeeEntryStream stream = APIClient.bungeeEntryStreamFromJson(message);
        switch (stream.getType()) {
            case SYNC:
                plugin.getProxyAPI().setBungeeEntry(stream.getEntry());
                break;
            case COMMAND:
                plugin.getLogger().info("Proxy dispatch received: /" + stream.getCommand());
                plugin.getServer().getCommandManager().executeAsync(plugin.getServer().getConsoleCommandSource(), stream.getCommand());
                break;
        }
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        plugin.getLogger().info("P Subscribed : " + pattern);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        plugin.getLogger().info("P UN Subscribed : " + pattern);
    }
}