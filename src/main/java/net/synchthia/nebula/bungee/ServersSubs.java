package net.synchthia.nebula.bungee;

import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.client.APIClient;
import redis.clients.jedis.JedisPubSub;

import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class ServersSyncSubs extends JedisPubSub {
    private static final NebulaPlugin plugin = NebulaPlugin.getPlugin();

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        NebulaProtos.ServerEntryStream serverStream = APIClient.entryStreamFromJson(message);
        switch (serverStream.getType()) {
            case SYNC:
                System.out.println(">>> " + serverStream);
                plugin.serverAPI.putServer(serverStream.getEntry());
                break;
            case REMOVE:
                plugin.serverAPI.removeServer(serverStream.getEntry().getName());
                break;
        }
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        plugin.getLogger().log(Level.INFO, "P Subscribed : " + pattern);
    }
}
