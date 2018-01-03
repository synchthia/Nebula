package net.synchthia.nebula.bungee.server;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Laica-Lunasys, misterT2525
 */
public class ServerSynchronizer {

    public static void putToLocalServer(Map<String, ServerInfo> serverInfo) {
        Map<String, ServerInfo> oldServers = new HashMap<>();
        Map<String, ServerInfo> newServers = new HashMap<>();
        Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();

        for (Map.Entry<String, ServerInfo> entry : servers.entrySet()) {
            oldServers.put(entry.getKey(), entry.getValue());
        }

        servers.clear();

        serverInfo.forEach((name, newInfo) -> {
            ServerInfo info = oldServers.get(newInfo.getName());

            if (info != null && !info.getName().equals(newInfo.getName())) {
                System.out.println("diff name");
                info = null;
            }
            if (info != null && !info.getAddress().getHostString().equals(newInfo.getAddress().getHostString())) {
                System.out.println("diff addr");
                info = null;
            }
            if (info != null && info.getAddress().getPort() != newInfo.getAddress().getPort()) {
                System.out.println("diff port");
                info = null;
            }
            if (info == null) {
                System.out.println(name + " is New or Changed.");
                info = ProxyServer.getInstance().constructServerInfo(newInfo.getName(), newInfo.getAddress(), newInfo.getMotd(), false);
            }

            servers.put(name, info);
        });
    }

    private static boolean setMotd(ServerInfo info, String motd) {
        return setFieldValue(info, "motd", motd);
    }

    private static boolean setRestricted(ServerInfo info, boolean restricted) {
        return setFieldValue(info, "restricted", restricted);
    }

    private static boolean setFieldValue(Object object, String field, Object value) {
        try {
            Field motdField = object.getClass().getDeclaredField(field);
            motdField.setAccessible(true);
            motdField.set(object, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
