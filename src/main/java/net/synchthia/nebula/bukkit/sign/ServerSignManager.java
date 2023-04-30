package net.synchthia.nebula.bukkit.sign;

import lombok.Getter;
import lombok.NonNull;
import net.synchthia.nebula.api.NebulaProtos;
import net.synchthia.nebula.bukkit.server.ServerAPI;
import net.synchthia.nebula.bukkit.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * @author misterT2525, Laica-Lunasys
 */

@Getter
public class ServerSignManager {

    private final JavaPlugin plugin;
    private final SignManager signManager = new SignManager();

    public ServerSignManager(@NonNull JavaPlugin plugin) {
        this.plugin = plugin;
        File signFile = new File(plugin.getDataFolder(), "signs.json");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        if (signFile.isFile()) {
            try {
                signManager.load(signFile);
            } catch (IOException e) {
                plugin.getLogger().log(Level.WARNING, "Failed to load sign data", e);
            }
        }
        Bukkit.getPluginManager().registerEvents(new ServerSignListener(this), plugin);
    }

    public void onDisable() {
        try {
            signManager.save(new File(plugin.getDataFolder(), "signs.json"));
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Failed to save sign data", e);
        }
    }

    public void updateSigns() {
        signManager.findAllSigns().forEach(sign -> {
            HashMap<Integer, String> format = getFormat(sign.getKey());
            Sign bukkitSign = sign.getSign();
            bukkitSign.setLine(0, format.get(0));
            bukkitSign.setLine(1, format.get(1));
            bukkitSign.setLine(2, format.get(2));
            bukkitSign.setLine(3, format.get(3));
            bukkitSign.update();
        });
    }


    public HashMap<Integer, String> getFormat(String key) {
        HashMap<Integer, String> format = new HashMap<>();
        NebulaProtos.ServerEntry server = ServerAPI.getServerEntry().get(key);

        if (server != null) {
            if (server.getStatus().getOnline()) {
                if (server.getStatus().getPlayers().getMax() == 0) {
                    format.put(0, "");
                    format.put(1, StringUtil.coloring("&1&l[" + server.getDisplayName() + "]"));
                    format.put(2, StringUtil.coloring("&8&l● STARTING ●"));
                    format.put(3, "");
                } else {
                    String motd = server.getStatus().getDescription();
                    if (motd.length() >= 15) {
                        motd = motd.substring(0, 14);
                    }

                    format.put(0, StringUtil.coloring("&1&l[" + server.getDisplayName() + "]"));
                    format.put(1, StringUtil.coloring(motd));
                    format.put(2, StringUtil.coloring("&8&l" + server.getStatus().getPlayers().getOnline() + "/" + server.getStatus().getPlayers().getMax()));
                    format.put(3, StringUtil.coloring("&1&l● ONLINE ●"));
                }
            } else {
                format.put(0, "");
                format.put(1, StringUtil.coloring("&1&l[" + server.getDisplayName() + "]"));
                format.put(2, StringUtil.coloring("&4&l■ OFFLINE ■"));
                format.put(3, "");
            }
        } else {
            format.put(0, "");
            format.put(1, "");
            format.put(2, "");
            format.put(3, "");
        }

        return format;
    }
}
