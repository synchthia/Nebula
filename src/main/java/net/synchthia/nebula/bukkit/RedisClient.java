package net.synchthia.nebula.bukkit;

import lombok.SneakyThrows;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class RedisClient {
    private JedisPool pool;
    private NebulaPlugin plugin = NebulaPlugin.getPlugin();
    private final String name;
    private final String hostname;
    private final Integer port;

    private ServersSubs serversSubs;

    public RedisClient(String name, String hostname, Integer port) {
        this.name = name;
        this.hostname = hostname;
        this.port = port;
        this.pool = new JedisPool(hostname, port);

        runTask();
    }

    private void runTask() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                try {
                    serversSubs = new ServersSubs(plugin);

                    plugin.getLogger().log(Level.INFO, "Connecting to Redis: " + hostname + ":" + port);
                    Jedis jedis = pool.getResource();

                    // Subscribe
                    jedis.psubscribe(serversSubs, "nebula.servers.global");
                } catch (Exception ex) {
                    plugin.getLogger().log(Level.WARNING, "Connection Error! Try Reconnecting every 3 seconds... : ", ex);
                    Thread.sleep(3000L);
                    runTask();
                }
            }
        });

    }

    public void disconnect() {
        if (serversSubs != null) {
            serversSubs.punsubscribe();
        }

        pool.close();
        pool.destroy();
    }
}
