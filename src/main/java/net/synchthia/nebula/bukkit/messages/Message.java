package net.synchthia.nebula.bukkit.messages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.Arrays;
import java.util.List;

public class Message {
    public static MiniMessage get() {
        return MiniMessage.miniMessage();
    }

    public static Component create(String input, TagResolver... resolver) {
        return MiniMessage.miniMessage().deserialize(input, resolver);
    }

    public static Component create(String[] inputs, TagResolver... resolver) {
        return MiniMessage.miniMessage().deserialize(String.join("<br/>", inputs), resolver);
    }

    public static String multilineFormat(String[] inputs) {
        List<String> list = Arrays.stream(inputs).filter(s -> !s.equals("")).toList();
        return String.join("<br/>", list);
    }

    public static Component showStatus(boolean value) {
        return value ? create("<green>ON</green>") : create("<red>OFF</red>");
    }

    public static Component createLegacy(String input) {
        return LegacyComponentSerializer.legacySection().deserialize(input);
    }
}
