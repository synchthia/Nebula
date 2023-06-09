package net.synchthia.nebula.bukkit.sign;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import net.synchthia.nebula.bukkit.messages.Message;
import net.synchthia.nebula.bukkit.server.ServerAction;
import org.bukkit.GameMode;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Optional;

/**
 * @author misterT2525
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ServerSignListener implements Listener {
    private static final String prefix = "[nebula]";

    private final NebulaPlugin plugin;
    private final ServerSignManager manager;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onCreateSign(SignChangeEvent event) {
        if (!event.getPlayer().hasPermission("nebula.sign.manage")) {
            return;
        }

        if (!PlainTextComponentSerializer.plainText().serializeOr(event.line(0), "").equalsIgnoreCase(prefix)) {
            return;
        }

        String key = PlainTextComponentSerializer.plainText().serializeOr(event.line(1), "");
        if (key.length() == 0) {
            return;
        }

        Component[] format = manager.getFormat(key);
        for (int i = 0; i < format.length; i++) {
            event.line(i, format[i]);
        }

        SignData sign = new SignData(event.getBlock(), key);
        sign.updateLines(prefix, sign.getKey(), "?????", "?????");
        manager.getSignManager().add(sign);
        event.getPlayer().sendMessage(Message.create("<green>Sign added: <server_name></green>", Placeholder.unparsed("server_name", sign.getKey())));
    }

    @EventHandler
    public void onDestroySign(BlockBreakEvent event) {
        if (!event.getPlayer().hasPermission("nebula.sign.manage") || !(event.getBlock().getState() instanceof Sign)) {
            return;
        }

        if (!event.getPlayer().isSneaking()) {
            return;
        }

        manager.getSignManager().findSign(event.getBlock()).ifPresent(signData -> {
            Optional<NebulaProtos.ServerEntry> server = plugin.getServerAPI().getServer(signData.getKey());
            manager.getSignManager().removeSign(event.getBlock());

            if (server.isPresent()) {
                event.getPlayer().sendMessage(Message.create("<red>Sign removed: <server_name></red>", Placeholder.unparsed("server_name", server.get().getDisplayName())));
            } else {
                event.getPlayer().sendMessage(Message.create("<red>Sign removed: <server_name></red>", Placeholder.unparsed("server_name", signData.getKey())));
            }
        });
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (!event.hasBlock()) {
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getPlayer().getGameMode() == GameMode.CREATIVE && event.getPlayer().isSneaking()) {
            return;
        }

        if (event.getClickedBlock() == null) {
            return;
        }

        manager.getSignManager().findSign(event.getClickedBlock()).ifPresent(sign -> {
            event.setCancelled(true);
            ServerAction.connect(plugin, event.getPlayer(), sign.getKey());
        });
    }
}
