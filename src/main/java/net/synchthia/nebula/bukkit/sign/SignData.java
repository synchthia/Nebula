package net.synchthia.nebula.bukkit.sign;

import lombok.Data;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

/**
 * @author misterT2525
 */
@Data
public class SignData {

    private final String world;
    private final int x;
    private final int y;
    private final int z;

    @NonNull
    private String key;

    public SignData(@NonNull Block block, @NonNull String key) {
        if (!(block.getState() instanceof Sign)) {
            throw new IllegalArgumentException("block must be sign");
        }

        this.world = block.getWorld().getName();
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.key = key;
    }

    public Block getBlock() {
        World world = Bukkit.getWorld(this.world);
        if (world == null) {
            return null;
        }
        Block block = world.getBlockAt(x, y, z);
        if (block.getState() instanceof Sign) {
            return block;
        }
        return null;
    }

    public Sign getSign() {
        Block block = getBlock();
        return block != null ? ((Sign) block.getState()) : null;
    }

    public String[] getLines() {
        Sign sign = getSign();
        if (sign == null) {
            throw new IllegalStateException("not sign block");
        }
        return sign.getLines();
    }

    public void updateLines(String... lines) {
        if (lines.length > 4) {
            throw new IllegalArgumentException("lines too long");
        }
        Sign sign = getSign();
        if (sign == null) {
            throw new IllegalStateException("not sign block");
        }

        for (int i = 0; i < lines.length; i++) {
            sign.setLine(i, lines[i]);
        }
        if (lines.length < 4) {
            for (int i = lines.length; i < 4; i++) {
                sign.setLine(i, "");
            }
        }
        sign.update();
    }
}
