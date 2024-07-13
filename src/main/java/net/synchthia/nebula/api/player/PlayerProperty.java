package net.synchthia.nebula.api.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.synchthia.nebula.api.NebulaProtos;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PlayerProperty {
    private String name;
    private String value;
    private String signature;

    public static List<PlayerProperty> fromProtobuf(List<NebulaProtos.PlayerProperty> pb) {
        List<PlayerProperty> entries = new ArrayList<>();
        for (NebulaProtos.PlayerProperty property : pb) {
            entries.add(new PlayerProperty(property.getName(), property.getValue(), property.getSignature()));
        }
        return entries;
    }

    public static List<NebulaProtos.PlayerProperty> toProtobuf(List<PlayerProperty> properties) {
        List<NebulaProtos.PlayerProperty> entries = new ArrayList<>();
        for (PlayerProperty property : properties) {
            entries.add(NebulaProtos.PlayerProperty.newBuilder()
                    .setName(property.getName())
                    .setValue(property.getValue())
                    .setSignature(property.getSignature())
                    .build());
        }
        return entries;
    }
}