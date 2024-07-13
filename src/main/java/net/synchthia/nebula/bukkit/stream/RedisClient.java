package net.synchthia.nebula.bukkit.stream;

import lombok.SneakyThrows;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class RedisClient {
    public final JedisPool pool;
    private final NebulaPlugin plugin = NebulaPlugin.getPlugin();
    private final String name;
    private final String hostname;
    private final Integer port;

    private ServersSubs serversSubs;
    private PlayerSubs playerSubs;

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

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                try {
                    playerSubs = new PlayerSubs(plugin);

                    plugin.getLogger().log(Level.INFO, "Connecting to Redis: " + hostname + ":" + port);
                    Jedis jedis = pool.getResource();

                    // Subscribe
                    jedis.psubscribe(playerSubs, "nebula.player.global");
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

        if (playerSubs != null) {
            playerSubs.punsubscribe();
        }

        pool.close();
        pool.destroy();
    }
}
