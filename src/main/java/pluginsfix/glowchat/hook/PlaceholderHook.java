package pluginsfix.glowchat.hook;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class PlaceholderHook {
    private static final boolean HOOKED = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;

    private PlaceholderHook() {
    }

    public static String setPlaceholders(Player player, String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        if (player != null && HOOKED) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }
}
