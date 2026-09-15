package pluginsfix.glowchat.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;

public final class ColorUtil {
    private static final Pattern HEX_PATTERN = Pattern.compile("#([A-Fa-f0-9]{6})");

    private ColorUtil() {
    }

    public static String colorize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String hex = matcher.group(1);
            ChatColor color = ChatColor.of("#" + hex);
            matcher.appendReplacement(buffer, color.toString());
        }
        matcher.appendTail(buffer);

        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }
}
