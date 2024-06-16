package net.synchthia.nebula.velocity;

import com.velocitypowered.api.network.ProtocolState;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.proxy.connection.MinecraftConnection;
import com.velocitypowered.proxy.connection.client.ConnectedPlayer;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import com.velocitypowered.proxy.protocol.StateRegistry;
import com.velocitypowered.proxy.protocol.packet.PluginMessagePacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.concurrent.TimeUnit;

public class CustomBrand {
    public static final ChannelIdentifier MODERN_CHANNEL = MinecraftChannelIdentifier.forDefaultNamespace("brand");
    private final NebulaVelocityPlugin plugin;
    private final boolean protocolError = false;

    public CustomBrand(NebulaVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    public void run() {
        plugin.getServer().getScheduler().buildTask(plugin, () -> plugin.getServer().getAllPlayers().forEach((player) -> {
            try {
                if (player.getProtocolState() != ProtocolState.PLAY) {
                    return;
                }
                final String brand = "Minecraft";
                MinecraftConnection connection = ((ConnectedPlayer) player).getConnection();
                if (connection.getState() != StateRegistry.PLAY) return;

                ProtocolVersion protocol = player.getProtocolVersion();
                if (protocol.compareTo(ProtocolVersion.MINECRAFT_1_13) < 0) {
                    if (!protocolError) {
                        plugin.getLogger().warn("Protocol version {} is not supported", protocol);
                    }
                    return;
                }

                ByteBuf buf = Unpooled.buffer();
                ProtocolUtils.writeString(buf, "Minecraft" + "§r");
                connection.write(new PluginMessagePacket(MODERN_CHANNEL.getId(), buf));
                connection.flush();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        })).repeat(100L, TimeUnit.MILLISECONDS).schedule();
    }
}
