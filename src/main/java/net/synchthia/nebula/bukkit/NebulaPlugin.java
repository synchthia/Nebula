package net.synchthia.nebula.bukkit;

import co.aikar.commands.BukkitCommandManager;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.SneakyThrows;
import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bukkit.command.ServerCommand;
import net.synchthia.nebula.bukkit.server.ServerAPI;
import net.synchthia.nebula.bukkit.sign.ServerSignManager;
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

    @Getter
    public APIClient apiClient;
    private String apiServerAddress;

    @Getter
    private ServerAPI serverAPI;

    @Getter
    private ServerSignManager serverSignManager;

    @Override
    public void onEnable() {
        plugin = this;
        try {
            // Register API
            registerAPI();
            registerStream();
            NebulaProtos.GetServerEntryResponse res = apiClient.getServerEntry().get(5, TimeUnit.SECONDS);
            res.getEntryList().forEach(ServerAPI::putServer);

            registerCommands();

            serverSignManager = new ServerSignManager(this);
            serverSignManager.updateSigns();

            this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

            getLogger().info(this.getName() + "Enabled!");
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Exception threw while onEnable.", e);
            setEnabled(false);
            plugin = null;
        }
    }

    @Override
    @SneakyThrows
    public void onDisable() {
        try {
            apiClient.shutdown();
        } catch (InterruptedException ex) {
            getLogger().log(Level.SEVERE, "Failed Shutdown Nebula-API", ex);
        }

        if (serverSignManager != null) {
            serverSignManager.onDisable();
        }

        getLogger().info(this.getName() + "Disabled");
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
    }

    public void registerStream() {
        apiClient.entryStream(Bukkit.getServer().getServerName());
    }

    private void registerCommands() {
        BukkitCommandManager manager = new BukkitCommandManager(this);

        manager.getCommandCompletions().registerCompletion("servers", c -> (
                Lists.newArrayList(ServerAPI.getServers().keySet())
        ));
        manager.registerCommand(new ServerCommand());
    }
}
