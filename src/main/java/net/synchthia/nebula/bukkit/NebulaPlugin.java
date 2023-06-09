package net.synchthia.nebula.bukkit;

import co.aikar.commands.BukkitCommandManager;
import lombok.Getter;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.command.LobbyCommand;
import net.synchthia.nebula.bukkit.command.ServerCommand;
import net.synchthia.nebula.bukkit.player.PlayerListener;
import net.synchthia.nebula.bukkit.server.ServerAPI;
import net.synchthia.nebula.bukkit.sign.ServerSignManager;
import net.synchthia.nebula.client.APIClient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class NebulaPlugin extends JavaPlugin {
    @Getter
    private static NebulaPlugin plugin;

    private static RedisClient redisClient;

    @Getter
    public APIClient apiClient;
    @Getter
    private ServerAPI serverAPI;

    @Getter
    private ServerSignManager serverSignManager;

    // Commands
    private BukkitCommandManager cmdManager;

    // Server Settings ==================
    @Getter
    private final static String serverId = System.getenv("SERVER_ID") != null ? System.getenv("SERVER_ID") : "unknown";

    @Getter
    private final static String serverName = System.getenv("SERVER_NAME") != null ? System.getenv("SERVER_NAME") : "Unknown";
    // ==================================

    @Override
    public void onEnable() {
        try {
            plugin = this;

            // Register Redis
            registerRedis();

            // Register API
            registerAPI();

            registerCommands();

            serverSignManager = new ServerSignManager(this);
            serverSignManager.updateSigns();

            this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

            Bukkit.getPluginManager().registerEvents(new PlayerListener(this), plugin);

            getLogger().log(Level.INFO, "Enabled " + this.getName());
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Exception threw while onEnable.", e);
            plugin = null;
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

        if (serverSignManager != null) {
            serverSignManager.onDisable();
        }

        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);

        getLogger().log(Level.INFO, "Disabled " + this.getName());
    }

    private void registerRedis() {
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
        redisClient = new RedisClient(NebulaPlugin.getServerId(), hostname, port);
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

        try {
            this.getServerAPI().fetch().get(5, TimeUnit.SECONDS);
        } catch (Exception ex) {
            getLogger().log(Level.WARNING, "Failed getServerEntry", ex);
        }
    }

    private void registerCommands() {
        this.cmdManager = new BukkitCommandManager(this);

        cmdManager.getCommandCompletions().registerCompletion("servers", c ->
                this.getServerAPI().getServers(c.getSender()).stream().map(NebulaProtos.ServerEntry::getName).toList()
        );

        cmdManager.registerCommand(new ServerCommand(this));
        cmdManager.registerCommand(new LobbyCommand(this));
    }
}
