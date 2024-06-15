package net.synchthia.nebula.velocity.server;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import net.synchthia.nebula.velocity.NebulaVelocityPlugin;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;

/**
 * @author Laica-Lunasys, misterT2525
 */
public class ServerSynchronizer {
    public static void putToLocalServer(NebulaVelocityPlugin plugin, String name, ServerInfo newInfo) {
        Collection<RegisteredServer> servers = plugin.getServer().getAllServers();
        Optional<RegisteredServer> server = servers.stream().filter((e) -> e.getServerInfo().getName().equals(newInfo.getName())).findAny();
        ServerInfo info = server.map(RegisteredServer::getServerInfo).orElse(null);
        ServerInfo oldInfo = info;

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
            // 新規登録じゃない場合は旧データがあるので削除
            if (oldInfo != null) {
                plugin.getServer().unregisterServer(oldInfo);
            }
            plugin.getServer().registerServer(newInfo);
        }
    }

    public static void removeLocalServer(NebulaVelocityPlugin plugin, String name) {
        Collection<RegisteredServer> servers = plugin.getServer().getAllServers();
        Optional<RegisteredServer> server = servers.stream().filter((e) -> e.getServerInfo().getName().equals(name)).findAny();
        ServerInfo info = server.map(RegisteredServer::getServerInfo).orElse(null);
        if (info == null) {
            System.out.println("???: " + name + " is null");
            return;
        }

        System.out.println("Unregister: " + name);
        plugin.getServer().unregisterServer(info);
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
