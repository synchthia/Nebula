package net.synchthia.nebula.bungee.stream;

import lombok.SneakyThrows;
import net.md_5.bungee.api.ProxyServer;
import net.synchthia.nebula.bungee.NebulaPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class RedisClient {
    private final JedisPool pool;
    private final NebulaPlugin plugin = NebulaPlugin.getPlugin();
    private final String name;
    private final String hostname;
    private final Integer port;

    private ServersSubs serversSubs;
    private BungeeSubs bungeeSubs;

    public RedisClient(String name, String hostname, Integer port) {
        this.name = name;
        this.hostname = hostname;
        this.port = port;
        this.pool = new JedisPool(hostname, port);
        runServerTask();
        runBungeeTask();
    }

    private void runServerTask() {
        ProxyServer.getInstance().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                try {
                    serversSubs = new ServersSubs();

                    plugin.getLogger().log(Level.INFO, "Connecting to Redis: " + hostname + ":" + port);
                    Jedis jedis = pool.getResource();

                    // Subscribe
                    jedis.psubscribe(serversSubs, "nebula.servers.global");
                } catch (Exception ex) {
                    plugin.getLogger().log(Level.WARNING, "Connection Error! Try Reconnecting every 3 seconds... : ", ex);
                    Thread.sleep(3000L);
                    runServerTask();
                }
            }
        });
    }

    private void runBungeeTask() {
        ProxyServer.getInstance().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                try {
                    bungeeSubs = new BungeeSubs();

                    plugin.getLogger().log(Level.INFO, "Connecting to Redis: " + hostname + ":" + port);
                    Jedis jedis = pool.getResource();

                    // Subscribe
                    jedis.psubscribe(bungeeSubs, "nebula.bungee.global");
                } catch (Exception ex) {
                    plugin.getLogger().log(Level.WARNING, "Connection Error! Try Reconnecting every 3 seconds... : ", ex);
                    Thread.sleep(3000L);
                    runBungeeTask();
                }
            }
        });
    }

    public void disconnect() {
        if (serversSubs != null) {
            serversSubs.punsubscribe();
        }

        if (bungeeSubs != null) {
            bungeeSubs.punsubscribe();
        }

        pool.close();
        pool.destroy();
    }
}
