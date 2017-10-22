package net.synchthia.nebula.bukkit.util;

import net.synchthia.nebula.bukkit.NebulaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeUtil {
    public static void connect(Player player, String serverName) {
        // Connect request
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteOut);
            dataOut.writeUTF("Connect");
            dataOut.writeUTF(serverName);

            player.sendPluginMessage(NebulaPlugin.getPlugin(), "BungeeCord", byteOut.toByteArray());
        } catch (IOException e) {
            player.sendMessage(ChatColor.RED + "An Internal Occurred. Please see Console.");
            e.printStackTrace();
        }
    }

    public static void disconnect(Player player, String reason) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteOut);
            dataOut.writeUTF("KickPlayer");
            dataOut.writeUTF(player.getName());
            dataOut.writeUTF(reason);

            player.sendPluginMessage(NebulaPlugin.getPlugin(), "BungeeCord", byteOut.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
