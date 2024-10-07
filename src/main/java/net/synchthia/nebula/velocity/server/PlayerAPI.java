package net.synchthia.nebula.velocity.server;

import com.velocitypowered.api.util.GameProfile;
import io.grpc.Status;
import lombok.RequiredArgsConstructor;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.velocity.NebulaVelocityPlugin;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class PlayerAPI {
    private final NebulaVelocityPlugin plugin;

    public CompletableFuture<NebulaProtos.IPLookupResponse> lookupIP(String playerIP) {
        return plugin.getApiClient().getIPLookup(playerIP).whenComplete((ipLookupResponse, throwable) -> {
            if (throwable != null) {
                if (Objects.equals(Status.fromThrowable(throwable).getDescription(), "context deadline exceeded")) {
                    plugin.getLogger().warn("Failed fetch Bungee Entry, retrying...");
                    lookupIP(playerIP).join();
                } else {
                    plugin.getLogger().warn("Failed lookup ip" + playerIP);
                }
            }
        });
    }

    public CompletableFuture<NebulaProtos.PlayerQuitResponse> requestPlayerQuit(NebulaProtos.PlayerProfile profile) {
        return plugin.getApiClient().playerQuit(profile).whenComplete((response, throwable) -> {
            if (throwable != null) {
                plugin.getLogger().warn("Failed process player Quit: Exception threw", throwable);
            }
        });
    }

    public Set<NebulaProtos.PlayerProperty> profileToProperties(GameProfile gameProfile) {
        Set<NebulaProtos.PlayerProperty> properties = new HashSet<>();
        for (GameProfile.Property property : gameProfile.getProperties()) {
            properties.add(NebulaProtos.PlayerProperty.newBuilder()
                    .setName(property.getName())
                    .setValue(property.getValue())
                    .setSignature(property.getSignature() != null ? property.getSignature() : "")
                    .build());
        }

        return properties;
    }
}
