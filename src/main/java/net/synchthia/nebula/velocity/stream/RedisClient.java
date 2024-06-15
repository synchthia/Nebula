package net.synchthia.nebula.velocity.stream;

import net.synchthia.nebula.velocity.NebulaVelocityPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Laica-Lunasys
 */
public class RedisClient {
    private final JedisPool pool;
    private final NebulaVelocityPlugin plugin;
    private final String name;
    private final String hostname;
    private final Integer port;

    private ServersSubs serversSubs;
    private BungeeSubs bungeeSubs;

    public RedisClient(NebulaVelocityPlugin plugin, String name, String hostname, Integer port) {
        this.plugin = plugin;
        this.name = name;
        this.hostname = hostname;
        this.port = port;
        this.pool = new JedisPool(hostname, port);
        runServerTask();
        runBungeeTask();
    }

    private void runServerTask() {
        plugin.getServer().getScheduler().buildTask(plugin, () -> {
            try {
                serversSubs = new ServersSubs(plugin);

                plugin.getLogger().info("Connecting to Redis: " + hostname + ":" + port);
                Jedis jedis = pool.getResource();

                // Subscribe
                jedis.psubscribe(serversSubs, "nebula.servers.global");
            } catch (Exception ex) {
                plugin.getLogger().warn("Connection Error! Try Reconnecting every 3 seconds... : ", ex);
                try {
                    Thread.sleep(3000L);
                    runServerTask();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).schedule();
    }

    private void runBungeeTask() {
        plugin.getServer().getScheduler().buildTask(plugin, () -> {
            try {
                bungeeSubs = new BungeeSubs(plugin);

                plugin.getLogger().info("Connecting to Redis: " + hostname + ":" + port);
                Jedis jedis = pool.getResource();

                // Subscribe
                jedis.psubscribe(bungeeSubs, "nebula.bungee.global");
            } catch (Exception ex) {
                plugin.getLogger().warn("Connection Error! Try Reconnecting every 3 seconds... : ", ex);
                try {
                    Thread.sleep(3000L);
                    runBungeeTask();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).schedule();
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
