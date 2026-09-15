package pluginsfix.glowchat.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class Text {

    private Text() {
    }

    public static String colorize(String text) {
        return ColorUtil.colorize(text);
    }

    public static void send(CommandSender sender, String message) {
        if (sender != null && message != null && !message.isEmpty()) {
            sender.sendMessage(colorize(message));
        }
    }

    public static void broadcast(String message) {
        if (message != null && !message.isEmpty()) {
            Bukkit.broadcastMessage(colorize(message));
        }
    }

    public static String setPlaceholders(Player player, String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        if (player != null && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }
}
