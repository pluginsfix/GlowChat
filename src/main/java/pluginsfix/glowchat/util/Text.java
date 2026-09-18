package pluginsfix.glowchat.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pluginsfix.glowchat.hook.PlaceholderHook;

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
        return PlaceholderHook.setPlaceholders(player, text);
    }
}
