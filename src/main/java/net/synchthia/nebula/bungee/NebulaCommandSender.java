package net.synchthia.nebula.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.Collection;
import java.util.Collections;

public class NebulaCommandSender implements CommandSender {
    private static final NebulaCommandSender instance;

    static {
        instance = new NebulaCommandSender();
    }

    public static NebulaCommandSender getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return "Nebula";
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void sendMessages(String... messages) {

    }

    @Override
    public void sendMessage(BaseComponent... message) {

    }

    @Override
    public void sendMessage(BaseComponent message) {

    }

    @Override
    public Collection<String> getGroups() {
        return null;
    }

    @Override
    public void addGroups(String... groups) {

    }

    @Override
    public void removeGroups(String... groups) {

    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public void setPermission(String permission, boolean value) {

    }

    @Override
    public Collection<String> getPermissions() {
        return Collections.emptySet();
    }
}
