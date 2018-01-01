package net.synchthia.nebula.bukkit;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class RedisClient {
    private static JedisPool pool;
    private NebulaPlugin plugin = NebulaPlugin.getPlugin();
    private String name;
    private String hostname;
    private Integer port;
    private Jedis jedis;

    private ServersSubs serversSubs;

    public RedisClient(String name, String hostname, Integer port) {
        this.name = name;
        this.hostname = hostname;
        this.port = port;
        runTask();
    }

    private void runTask() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                try {
                    serversSubs = new ServersSubs();

                    plugin.getLogger().log(Level.INFO, "Connecting to Redis: " + hostname + ":" + port);
                    pool = new JedisPool(hostname, port);
                    jedis = pool.getResource();
                    jedis.connect();

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
