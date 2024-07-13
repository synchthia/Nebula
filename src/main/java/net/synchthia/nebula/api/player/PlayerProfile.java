package net.synchthia.nebula.api.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.synchthia.nebula.api.NebulaProtos;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PlayerProfile {
    private UUID uuid;
    private String name;
    private long latency;
    private String currentServer;
    private List<PlayerProperty> properties;
    private boolean hide;

    public static PlayerProfile fromProtobuf(NebulaProtos.PlayerProfile pb) {
        return new PlayerProfile(
                UUID.fromString(pb.getPlayerUUID()),
                pb.getPlayerName(),
                pb.getPlayerLatency(),
                pb.getCurrentServer(),
                PlayerProperty.fromProtobuf(pb.getPropertiesList()),
                pb.getHide()
        );
    }

    public NebulaProtos.PlayerProfile toProtobuf() {
        return NebulaProtos.PlayerProfile.newBuilder()
                .setPlayerUUID(this.getUuid().toString())
                .setPlayerName(this.getName())
                .setPlayerLatency(this.getLatency())
                .setCurrentServer(this.getCurrentServer())
                .addAllProperties(PlayerProperty.toProtobuf(this.getProperties()))
                .setHide(this.isHide())
                .build();
    }
}
