package net.synchthia.nebula.velocity.server;

import com.velocitypowered.api.util.Favicon;
import io.grpc.Status;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.velocity.NebulaVelocityPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ProxyAPI {
    private final NebulaVelocityPlugin plugin;

    @Getter
    private Component motd;

    @Getter
    private Favicon favicon;

    public ProxyAPI(NebulaVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    public CompletableFuture<NebulaProtos.GetBungeeEntryResponse> fetchBungeeEntry() {
        return plugin.getApiClient().getBungeeEntry().whenComplete((response, throwable) -> {
            if (throwable != null) {
                if (Objects.equals(Status.fromThrowable(throwable).getDescription(), "context deadline exceeded")) {
                    plugin.getLogger().warn("Failed fetch Bungee Entry, retrying...");
                    fetchBungeeEntry().join();
                } else {
                    plugin.getLogger().warn("Failed fetch Bungee Entry", throwable);
                }

                return;
            }
            setBungeeEntry(response.getEntry());
        });
    }

    public void setBungeeEntry(NebulaProtos.BungeeEntry entry) {
        setMotd(entry.getMotd());

        if (!entry.getFavicon().equals("")) {
            setFavicon(entry.getFavicon());
        }
    }

    private void setMotd(String motd) {
        this.motd = MiniMessage.miniMessage().deserialize(motd);
    }

    private void setFavicon(String b64Favicon) {
        try {
            byte[] decoded = Base64.getDecoder().decode(b64Favicon.replaceFirst("data:image/png;base64,", ""));

            ByteArrayInputStream ba = new ByteArrayInputStream(decoded);
            BufferedImage read = ImageIO.read(ba);
            this.favicon = Favicon.create(read);
        } catch (IOException ex) {
            plugin.getLogger().warn("Failed Set Favicon", ex);
        }
    }
}
