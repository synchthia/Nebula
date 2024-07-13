package net.synchthia.nebula.bukkit.player;

import com.destroystokyo.paper.profile.ProfileProperty;
import lombok.RequiredArgsConstructor;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.api.player.PlayerProfile;
import net.synchthia.nebula.api.player.PlayerProperty;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

@RequiredArgsConstructor
public class PlayerAPI {
    private final NebulaPlugin plugin;
    private final Map<UUID, PlayerProfile> players = new HashMap<>();

    public CompletableFuture<NebulaProtos.FetchAllPlayersResponse> fetchAllPlayers() {
        return plugin.getApiClient().fetchAllPlayers().whenComplete((res, throwable) -> {
            if (throwable != null) {
                plugin.getLogger().log(Level.WARNING, "Failed fetch players: ", throwable);
                return;
            }

            updatePlayers(res.getProfilesList());
        });
    }

    public void updatePlayers(List<NebulaProtos.PlayerProfile> profiles) {
        for (NebulaProtos.PlayerProfile profile : profiles) {
            players.put(UUID.fromString(profile.getPlayerUUID()), PlayerProfile.fromProtobuf(profile));
        }
    }

    public void addPlayer(NebulaProtos.PlayerProfile profile) {
        players.put(UUID.fromString(profile.getPlayerUUID()), PlayerProfile.fromProtobuf(profile));
    }

    public void removePlayer(NebulaProtos.PlayerProfile profile) {
        players.remove(UUID.fromString(profile.getPlayerUUID()));
    }

    public CompletableFuture<NebulaProtos.UpdateAllPlayersResponse> updateAllPlayers(Collection<? extends Player> players) {
        List<NebulaProtos.PlayerProfile> profiles = new ArrayList<>();
        for (Player player : players) {
            profiles.add(playerToProfile(player).toProtobuf());
        }

        return plugin.getApiClient().updateAllPlayers(profiles).whenComplete((response, throwable) -> {
            if (throwable != null) {
                plugin.getLogger().log(Level.WARNING, "Failed update all players: Exception threw", throwable);
            }
        });
    }

    public PlayerProfile playerToProfile(Player player) {
        List<PlayerProperty> properties = new ArrayList<>();
        for (ProfileProperty property : player.getPlayerProfile().getProperties()) {
            properties.add(new PlayerProperty(property.getName(), property.getValue(), property.getSignature()));
        }

        return new PlayerProfile(
                player.getUniqueId(),
                player.getName(),
                player.getPing(),
                NebulaPlugin.getServerId(),
                properties,
                PlayerUtil.isPlayerVanished(player)
        );
    }

    public CompletableFuture<NebulaProtos.PlayerLoginResponse> requestPlayerLogin(NebulaProtos.PlayerProfile profile) {
        return plugin.getApiClient().playerLogin(profile).whenComplete((response, throwable) -> {
            if (throwable != null) {
                plugin.getLogger().log(Level.WARNING, "Failed process player Login: Exception threw", throwable);
            }
        });
    }

    public CompletableFuture<NebulaProtos.PlayerQuitResponse> requestPlayerQuit(NebulaProtos.PlayerProfile profile) {
        return plugin.getApiClient().playerQuit(profile).whenComplete((response, throwable) -> {
            if (throwable != null) {
                plugin.getLogger().log(Level.WARNING, "Failed process player Quit: Exception threw", throwable);
            }
        });
    }
}
