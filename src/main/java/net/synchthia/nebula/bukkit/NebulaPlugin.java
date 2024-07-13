package net.synchthia.nebula.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import net.synchthia.nebula.api.APIClient;
import net.synchthia.nebula.bukkit.command.ListServersCommand;
import net.synchthia.nebula.bukkit.command.LobbyCommand;
import net.synchthia.nebula.bukkit.command.NebulaCommand;
import net.synchthia.nebula.bukkit.command.ServerCommand;
import net.synchthia.nebula.bukkit.player.PlayerAPI;
import net.synchthia.nebula.bukkit.player.PlayerListener;
import net.synchthia.nebula.bukkit.player.PlayerProfileScheduler;
import net.synchthia.nebula.bukkit.server.ServerAPI;
import net.synchthia.nebula.bukkit.sign.ServerSignManager;
import net.synchthia.nebula.bukkit.stream.RedisClient;
import net.synchthia.nebula.bukkit.tablist.TabList;
import net.synchthia.nebula.bukkit.tablist.TabListListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.exception.InvalidSyntaxException;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.LegacyPaperCommandManager;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author Laica-Lunasys
 */
public class NebulaPlugin extends JavaPlugin {
    @Getter
    private static NebulaPlugin plugin;

    @Getter
    private RedisClient redisClient;

    @Getter
    public APIClient apiClient;
    @Getter
    private ServerAPI serverAPI;
    @Getter
    private PlayerAPI playerAPI;

    @Getter
    private ServerSignManager serverSignManager;

    @Getter
    private ProtocolManager protocolManager;

    @Getter
    private PlayerProfileScheduler playerProfileScheduler;

    @Getter
    private TabList tabList;

    // Commands
    private AnnotationParser<CommandSender> annotationParser;
    private LegacyPaperCommandManager<CommandSender> commandManager;

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

            this.protocolManager = ProtocolLibrary.getProtocolManager();
            this.tabList = new TabList(plugin);
            this.tabList.registerListener();

            // Register Redis
            registerRedis();

            // Register API
            registerAPI();

            registerCommands();

            serverSignManager = new ServerSignManager(this);
            serverSignManager.updateSigns();

            playerProfileScheduler = new PlayerProfileScheduler(this);
            playerProfileScheduler.run();

            this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

            Bukkit.getPluginManager().registerEvents(new PlayerListener(this), plugin);
            Bukkit.getPluginManager().registerEvents(new TabListListener(this), plugin);

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
        playerAPI = new PlayerAPI(this);

        try {
            this.getServerAPI().fetch().get(5, TimeUnit.SECONDS);
        } catch (Exception ex) {
            getLogger().log(Level.WARNING, "Failed getServerEntry", ex);
        }

        try {
            this.getPlayerAPI().fetchAllPlayers().get(5, TimeUnit.SECONDS);
        } catch (Exception ex) {
            getLogger().log(Level.WARNING, "Failed fetchAllPlayers", ex);
        }
    }

    private void registerCommands() {
        this.commandManager = LegacyPaperCommandManager.createNative(plugin, ExecutionCoordinator.simpleCoordinator());

        if (this.commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            this.commandManager.registerAsynchronousCompletions();
        }

        this.commandManager.exceptionController().registerHandler(InvalidSyntaxException.class, e -> {
            e.context().sender().sendRichMessage(String.format("<yellow>Usage:</yellow> <green>/%s</green>", e.exception().correctSyntax()));
        });

        this.annotationParser = new AnnotationParser<>(this.commandManager, CommandSender.class);

        this.annotationParser.parse(
                new CommandSuggestions(this),
                new NebulaCommand(this),
                new LobbyCommand(this),
                new ServerCommand(this),
                new ListServersCommand(this)
        );
    }
}
