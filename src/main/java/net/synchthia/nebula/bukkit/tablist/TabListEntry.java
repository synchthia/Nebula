package net.synchthia.nebula.bukkit.tablist;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.synchthia.nebula.api.player.PlayerProperty;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TabListEntry {
    private UUID uuid;
    private String name;
    private long latency;
    private List<PlayerProperty> properties;
    private boolean hide;
}