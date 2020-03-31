package net.synchthia.nebula.bungee;

import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.synchthia.nebula.bungee.command.QuitCommand;
import net.synchthia.nebula.bungee.event.PingListener;
import net.synchthia.nebula.bungee.event.PlayerListener;
import net.synchthia.nebula.bungee.server.ProxyAPI;
import net.synchthia.nebula.bungee.server.ServerAPI;
import net.synchthia.nebula.bungee.stream.RedisClient;
import net.synchthia.nebula.client.APIClient;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class NebulaPlugin extends Plugin {
    @Getter
    public static NebulaPlugin plugin;

    private static RedisClient redisClient;

    @Getter
    public APIClient apiClient;
    @Getter
    public ServerAPI serverAPI;
    @Getter
    public ProxyAPI proxyAPI;

    @Override
    public void onEnable() {
        try {
            plugin = this;

            // Clear All Servers
            ProxyServer.getInstance().getServers().clear();

            // Register Redis
            registerRedis();

            // Register API
            registerAPI();

            // Register Listener
            ProxyServer.getInstance().getPluginManager().registerListener(this, new PingListener());
            ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerListener());

            // Register Command
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new QuitCommand());

            getLogger().log(Level.INFO, "Enabled Nebula");
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "An internal exception occurred at Enabling", e);
            plugin = null;
        }
    }

    public void registerRedis() {
        String hostname = "localhost";
        Integer port = 6379;

        String redisAddress = System.getenv("NEBULA_REDIS_ADDRESS");
        if (redisAddress != null) {
            if (redisAddress.contains(":")) {
                String[] splited = redisAddress.split(":");
                hostname = splited[0];
                port = Integer.valueOf(splited[1]);
            } else {
                hostname = redisAddress;
            }
        }

        getLogger().log(Level.INFO, "Redis Address: " + hostname + ":" + port);
        redisClient = new RedisClient("bungee-" + UUID.randomUUID().toString(), hostname, port);

    }

    private void registerAPI() {
        String apiAddress = System.getenv("NEBULA_API_ADDRESS");
        if (apiAddress == null) {
            apiAddress = "localhost:17200";
        }
        getLogger().log(Level.INFO, "API Address: " + apiAddress);

        // New API Client
        apiClient = new APIClient(apiAddress);

        // Activate API
        serverAPI = new ServerAPI(this);
        proxyAPI = new ProxyAPI(this);

        // Get Bungee Information
        try {
            proxyAPI.fetchBungeeEntry().get(5, TimeUnit.SECONDS);
        } catch (Exception ex) {
            plugin.getLogger().log(Level.WARNING, "Failed Fetch Bungee Entry", ex);
        }

        // Get Servers
        try {
            serverAPI.getServersFromAPI().get(5, TimeUnit.SECONDS);
        } catch (Exception ex) {
            getLogger().log(Level.WARNING, "Failed getServersFromAPI", ex);
        }
    }

    @Override
    public void onDisable() {
        try {
            apiClient.shutdown();
            redisClient.disconnect();
        } catch (InterruptedException ex) {
            getLogger().log(Level.SEVERE, "Failed Shutdown Nebula-API", ex);
        }

        // Disabled!
        getLogger().log(Level.INFO, "Disabled Nebula");
        plugin = null;
    }
}
