package net.synchthia.nebula.bukkit;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class RedisClient {
    private NebulaPlugin plugin = NebulaPlugin.getPlugin();

    private static BukkitTask task;
    private static Boolean wantClose = false;
    private String name;
    private String hostname;
    private Integer port;
    private JedisPool pool;
    private Jedis jedis;


    public RedisClient(String name, String hostname, Integer port) {
        this.name = name;
        this.hostname = hostname;
        this.port = port;
        runTask();
    }

    private void runTask() {
        task = Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                try {
                    plugin.getLogger().log(Level.INFO, "Connecting to Redis: " + hostname + ":" + port);
                    pool = new JedisPool(hostname, port);
                    jedis = pool.getResource();
                    jedis.connect();

                    // Subscribe
                    jedis.psubscribe(new ServersSubs(), "nebula.servers.global");
                } catch (Exception ex) {
                    if (wantClose) {
                        plugin.getLogger().log(Level.INFO, "Disconnecting from Redis...");
                        jedis.unwatch();
                        jedis.disconnect();
                    } else {
                        ex.printStackTrace();

                        plugin.getLogger().log(Level.WARNING, "Connection Error! Try Reconnecting every 3 seconds...");
                        Thread.sleep(3000L);
                        runTask();
                    }
                }
            }
        });

    }

    public void disconnect() {
        wantClose = true;
        jedis.disconnect();
        pool.destroy();

        task.cancel();
    }
}
