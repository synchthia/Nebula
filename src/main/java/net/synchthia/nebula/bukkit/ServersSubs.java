package net.synchthia.nebula.bukkit;

import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bukkit.server.ServerAPI;
import net.synchthia.nebula.client.APIClient;
import redis.clients.jedis.JedisPubSub;

import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class ServersSubs extends JedisPubSub {
    private static final NebulaPlugin plugin = NebulaPlugin.getPlugin();

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        NebulaProtos.ServerEntryStream serverStream = APIClient.entryStreamFromJson(message);
        switch (serverStream.getType()) {
            case SYNC:
                System.out.println(">>> " + serverStream);
                NebulaProtos.ServerEntry entry = serverStream.getEntry();
                ServerAPI.putServer(entry);
                plugin.getServerSignManager().updateSigns();
                break;
            case REMOVE:
                ServerAPI.removeServer(serverStream.getEntry().getName());
                plugin.getServerSignManager().updateSigns();
                break;
        }
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        plugin.getLogger().log(Level.INFO, "P Subscribed : " + pattern);
    }
}

