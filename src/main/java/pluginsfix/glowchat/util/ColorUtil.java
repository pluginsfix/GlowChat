package pluginsfix.glowchat.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

public final class ColorUtil {
    private static final Pattern HEX_PATTERN = Pattern.compile("#([A-Fa-f0-9]{6})");
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.legacySection();

    private ColorUtil() {
    }

    public static String colorize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        if (text.contains("<") && text.contains(">")) {
            try {
                Component component = MINI_MESSAGE.deserialize(text);
                text = LEGACY_SERIALIZER.serialize(component);
            } catch (Exception ignored) {
            }
        }

        if (text.contains("#")) {
            Matcher matcher = HEX_PATTERN.matcher(text);
            StringBuffer buffer = new StringBuffer();
            while (matcher.find()) {
                String hex = matcher.group(1);
                ChatColor color = ChatColor.of("#" + hex);
                matcher.appendReplacement(buffer, color.toString());
            }
            matcher.appendTail(buffer);
            text = buffer.toString();
        }

        if (text.contains("&")) {
            text = ChatColor.translateAlternateColorCodes('&', text);
        }

        return text;
    }
}
