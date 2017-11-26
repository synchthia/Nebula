package net.synchthia.nebula.bungee;

import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.synchthia.nebula.bungee.event.PingListener;
import net.synchthia.nebula.bungee.server.ServerAPI;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class NebulaPlugin extends Plugin {
    @Getter
    public static NebulaPlugin plugin;

    @Getter
    public APIClient apiClient;
    @Getter
    public ServerAPI serverAPI;
    private String apiServerAddress;

    @Override
    public void onEnable() {
        try {
            plugin = this;

            // Clear All Servers
            ProxyServer.getInstance().getServers().clear();

            // Register API
            registerAPI();
            registerStream();

            ProxyServer.getInstance().getPluginManager().registerListener(this, new PingListener());
            getLogger().log(Level.INFO, "Enabled Nebula");
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "An internal exception occurred at Enabling", e);
            plugin = null;
        }
    }

    private void registerAPI() {
        String addressEnv = System.getenv("NEBULA_API_ADDRESS");
        if (addressEnv == null) {
            apiServerAddress = "localhost:17200";
        } else {
            apiServerAddress = addressEnv;
        }
        getLogger().log(Level.INFO, "API Address: " + apiServerAddress);

        // New API Client
        apiClient = new APIClient(apiServerAddress);

        // Activate API
        serverAPI = new ServerAPI(this);

        // Get Servers
        try {
            serverAPI.getServers().get(5, TimeUnit.SECONDS);
        } catch (Exception ex) {
            getLogger().log(Level.WARNING, "Failed getServers", ex);
        }
    }

    public void registerStream() {
        apiClient.entryStream(ProxyServer.getInstance().getName());
    }

    @Override
    public void onDisable() {
        try {
            apiClient.shutdown();
        } catch (InterruptedException ex) {
            getLogger().log(Level.SEVERE, "Failed Shutdown Nebula-API", ex);
        }

        // Disabled!
        getLogger().log(Level.INFO, "Disabled Nebula");
        plugin = null;
    }
}
