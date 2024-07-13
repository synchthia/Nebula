package net.synchthia.nebula.bukkit.command;

import lombok.RequiredArgsConstructor;
import net.synchthia.nebula.bukkit.NebulaPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Permission;

@RequiredArgsConstructor
public class NebulaCommand {
    private final NebulaPlugin plugin;

    @Command("nebula")
    @Permission("nebula.command.nebula")
    public void onNebula(CommandSender sender) {
        sender.sendRichMessage("<gray>Nebula: </gray>" + plugin.getName());
    }

    @Command("nebula tablist")
    @Permission("nebula.command.extra")
    public void onTabList(Player player) {
        plugin.getTabList().removeAll();

//        plugin.getTabList().addPlayer(player.getUniqueId(), player.getName());
//        plugin.getTabList().addPlayer(UUID.fromString("e70aec19-6eb2-42d6-a820-075c0e0dec76"), "Laica_Lunasys");
//
//        plugin.getTabList().addPlayer(UUID.fromString("83b43ad1-2168-478d-86d5-b962a8c87dfe"), "TEST_1");
//        plugin.getTabList().addPlayer(UUID.fromString("48a2d963-e505-44f3-86c6-fe1737931b59"), "TEST_2");
//        plugin.getTabList().addPlayer(UUID.fromString("769313f2-e471-42d8-98ef-bbe1809bd33a"), "TEST_3");
//        plugin.getTabList().addPlayer(UUID.fromString("0da6713e-486e-4959-be1b-89c15014b36b"), "TEST_4");
//        plugin.getTabList().addPlayer(UUID.fromString("39a90cfb-1738-4b01-b8a7-1cc7bf276756"), "TEST_5");
//        plugin.getTabList().addPlayer(UUID.fromString("0d41cb93-da2f-4d82-879e-b3e24f810446"), "TEST_6");
//        plugin.getTabList().addPlayer(UUID.fromString("42712350-50fa-45ea-9454-d7e11c2b99c6"), "TEST_7");
//        plugin.getTabList().addPlayer(UUID.fromString("d67a7b76-5985-4b5a-befd-e15966ec9350"), "TEST_8");
//        plugin.getTabList().addPlayer(UUID.fromString("9ceed9e9-1691-404d-970f-7bd794efca95"), "TEST_9");
//        plugin.getTabList().addPlayer(UUID.fromString("07f7a009-ee8f-4a92-9f38-46829a190cb2"), "TEST_10");
    }

    @Command("nebula removetablist")
    @Permission("nebula.command.extra")
    public void onRemoveTabList() {
        plugin.getTabList().removeAll();
    }

    @Command("nebula synctablist")
    @Permission("nebula.command.extra")
    public void onSyncTabList(Player player) {
        plugin.getTabList().syncTabList(player);
    }
}
