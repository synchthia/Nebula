package net.synchthia.nebula.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ListenerCloseEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import net.synchthia.nebula.api.APIClient;
import net.synchthia.nebula.velocity.listener.PingListener;
import net.synchthia.nebula.velocity.listener.PlayerListener;
import net.synchthia.nebula.velocity.server.PlayerAPI;
import net.synchthia.nebula.velocity.server.ProxyAPI;
import net.synchthia.nebula.velocity.server.ServerAPI;
import net.synchthia.nebula.velocity.stream.RedisClient;
import org.slf4j.Logger;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Laica-Lunasys
 */
@Plugin(id = "nebula", name = "Nebula", version = "1.0.0", url = "https://synchthia.net", description = "Server Synchronize system", authors = {"SYNCHTHIA"})
public class NebulaVelocityPlugin {
    // Server Settings ==================
    @Getter
    private final static Boolean isIPFilterEnable = System.getenv("ENABLE_IP_FILTER") != null && System.getenv("ENABLE_IP_FILTER").equals("true");
    // ==================================

    @Getter
    public static NebulaVelocityPlugin plugin;

    private static RedisClient redisClient;

    @Getter
    private APIClient apiClient;
    @Getter
    private ProxyAPI proxyAPI;
    @Getter
    private ServerAPI serverAPI;
    @Getter
    private PlayerAPI playerAPI;

    @Getter
    private final ProxyServer server;

    @Getter
    private final Logger logger;

    @Inject
    public NebulaVelocityPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("Initialization Proxy...");
        // Clear all servers
        server.getAllServers().forEach((s) -> {
            server.unregisterServer(s.getServerInfo());
        });


        try {
            // Connect to Redis (for stream)
            registerRedis();

            // Register API
            registerAPI();

            // Register Listener
            this.server.getEventManager().register(this, new PingListener(this));
            this.server.getEventManager().register(this, new PlayerListener(this));

            // Brand Sync
            CustomBrand customBrand = new CustomBrand(this);
            customBrand.run();

            // Disable built-in commands
            this.server.getCommandManager().unregister("server");
            // this.server.getCommandManager().unregister("glist");
        } catch (Exception ex) {
            this.logger.error("Failed initialize nebula! server will be shutdown...", ex);
            server.shutdown();
            return;
        }

        logger.info("Enabled Nebula!");
    }

    public void registerRedis() {
        String hostname = "localhost";
        int port = 6379;

        String redisAddress = System.getenv("NEBULA_REDIS_ADDRESS");
        if (redisAddress != null) {
            if (redisAddress.contains(":")) {
                String[] splited = redisAddress.split(":");
                hostname = splited[0];
                port = Integer.parseInt(splited[1]);
            } else {
                hostname = redisAddress;
            }
        }

        this.logger.info(String.format("Redis Address: %s:%d", hostname, port));
        redisClient = new RedisClient(this, "bungee-" + UUID.randomUUID(), hostname, port);
    }

    private void registerAPI() {
        String apiAddress = System.getenv("NEBULA_API_ADDRESS");
        if (apiAddress == null) {
            apiAddress = "localhost:17200";
        }
        getLogger().info("API Address: " + apiAddress);

        // New API Client
        this.apiClient = new APIClient(apiAddress);

        // Activate API
        proxyAPI = new ProxyAPI(this);
        serverAPI = new ServerAPI(this);
        playerAPI = new PlayerAPI(this);

        // Get Bungee Information
        try {
            proxyAPI.fetchBungeeEntry().get(5, TimeUnit.SECONDS);
        } catch (Exception ex) {
            getLogger().warn("Failed Fetch Bungee Entry", ex);
        }

        // Get Servers
        try {
            serverAPI.getServersFromAPI().get(5, TimeUnit.SECONDS);
        } catch (Exception ex) {
            getLogger().warn("Failed getServersFromAPI", ex);
        }
    }

    @Subscribe
    public void onListenerClose(ListenerCloseEvent event) {
        try {
            redisClient.disconnect();
            apiClient.shutdown();
        } catch (InterruptedException ex) {
            getLogger().error("Failed shutdown nebula", ex);
        }

        getLogger().info("Disabled Nebula!");
        plugin = null;
    }
}