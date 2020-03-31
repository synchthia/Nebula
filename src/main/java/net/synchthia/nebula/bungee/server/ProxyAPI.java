package net.synchthia.nebula.bungee.server;

import io.grpc.Status;
import lombok.Getter;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.synchthia.api.nebula.NebulaProtos;
import net.synchthia.nebula.bungee.NebulaPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class ProxyAPI {
    private NebulaPlugin plugin;

    @Getter
    private BaseComponent motd;

    @Getter
    private Favicon favicon;

    public ProxyAPI(NebulaPlugin plugin) {
        this.plugin = plugin;
    }

    public CompletableFuture<NebulaProtos.GetBungeeEntryResponse> fetchBungeeEntry() {
        return plugin.apiClient.getBungeeEntry().whenComplete((response, throwable) -> {
            if (throwable != null) {
                if (Objects.equals(Status.fromThrowable(throwable).getDescription(), "context deadline exceeded")) {
                    plugin.getLogger().log(Level.WARNING, "Failed fetch Bungee Entry, retrying...");
                    fetchBungeeEntry().join();
                } else {
                    plugin.getLogger().log(Level.WARNING, "Failed fetch Bungee Entry", throwable);
                }

                return;
            }
            setBungeeEntry(response.getEntry());
        });
    }

    public void setBungeeEntry(NebulaProtos.BungeeEntry entry) {
        setMotd(entry.getMotd());
        setFavicon(entry.getFavicon());
    }

    private void setMotd(String motd) {
        TextComponent tc = new TextComponent();
        tc.setText(motd.replace("\\n", "\n"));

        this.motd = tc;
    }

    private void setFavicon(String b64Favicon) {
        try {
            byte[] decoded = Base64.getDecoder().decode(b64Favicon.replaceFirst("data:image/png;base64,", ""));

            ByteArrayInputStream ba = new ByteArrayInputStream(decoded);
            BufferedImage read = ImageIO.read(ba);
            this.favicon = Favicon.create(read);

        } catch (IOException ex) {
            plugin.getLogger().log(Level.WARNING, "Failed Set Favicon", ex);
        }
    }
}
