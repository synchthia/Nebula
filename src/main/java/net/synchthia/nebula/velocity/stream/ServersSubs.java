package net.synchthia.nebula.velocity.stream;

import net.synchthia.nebula.api.APIClient;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.velocity.NebulaVelocityPlugin;
import redis.clients.jedis.JedisPubSub;

/**
 * @author Laica-Lunasys
 */
public class ServersSubs extends JedisPubSub {
    private final NebulaVelocityPlugin plugin;

    public ServersSubs(NebulaVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        NebulaProtos.ServerEntryStream serverStream = APIClient.serverEntryStreamFromJson(message);
        switch (serverStream.getType()) {
            case SYNC:
                plugin.getServerAPI().putServer(serverStream.getEntry());
                break;
            case REMOVE:
                plugin.getServerAPI().removeServer(serverStream.getEntry().getName());
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
