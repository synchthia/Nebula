package net.synchthia.nebula.bukkit;

import co.aikar.commands.BukkitCommandManager;
import com.google.common.collect.Lists;
import lombok.Getter;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.command.LobbyCommand;
import net.synchthia.nebula.bukkit.command.ServerCommand;
import net.synchthia.nebula.bukkit.player.PlayerAPI;
import net.synchthia.nebula.bukkit.player.PlayerListener;
import net.synchthia.nebula.bukkit.server.ServerAPI;
import net.synchthia.nebula.bukkit.sign.ServerSignListener;
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
    private PlayerAPI playerAPI;

    @Getter
    private ServerSignManager serverSignManager;

    // Server Settings ==================
    @Getter
    private final static String serverId = System.getenv("SERVER_ID") != null ? System.getenv("SERVER_ID") : "unknown";

    @Getter
    private final static String serverName = System.getenv("SERVER_NAME") != null ? System.getenv("SERVER_NAME") : "Unknown";

    @Getter
    private final static Boolean isIPFilterEnable = System.getenv("ENABLE_IP_FILTER").equals("true");
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
            setEnabled(false);
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

        getLogger().log(Level.INFO, "Disabled " + this.getName());
    }

    private void registerRedis() throws InterruptedException {
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

        getLogger().log(Level.INFO, "Redis Address: " + redisAddress);
        redisClient = new RedisClient(NebulaPlugin.getServerName(), hostname, port);
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
        playerAPI = new PlayerAPI(this);

        try {
            NebulaProtos.GetServerEntryResponse res = apiClient.getServerEntry().get(5, TimeUnit.SECONDS);
            res.getEntryList().forEach(ServerAPI::putServer);
        } catch (Exception ex) {
            getLogger().log(Level.WARNING, "Failed getServerEntry", ex);

        }
    }

    private void registerCommands() {
        BukkitCommandManager manager = new BukkitCommandManager(this);

        manager.getCommandCompletions().registerCompletion("servers", c -> (
                Lists.newArrayList(ServerAPI.getServerEntry().keySet())
        ));
        manager.registerCommand(new ServerCommand());
        manager.registerCommand(new LobbyCommand());
    }
}
