package net.synchthia.nebula.bukkit.tablist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import net.synchthia.nebula.api.player.PlayerProperty;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@ToString
public class TabListEntry {
    private UUID uuid;
    private String name;
    private long latency;
    private String currentServer;
    private List<PlayerProperty> properties;
    private boolean hide;
}