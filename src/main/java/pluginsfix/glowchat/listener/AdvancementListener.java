package pluginsfix.glowchat.listener;

import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import pluginsfix.glowchat.GlowChat;
import pluginsfix.glowchat.util.Text;

public class AdvancementListener implements Listener {
    private final GlowChat plugin;

    public AdvancementListener(GlowChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        if (!plugin.getConfig().getBoolean("advancement.enabled", true)) {
            return;
        }
        if (plugin.getConfig().getBoolean("advancement.disable", false)) {
            return;
        }

        Advancement advancement = event.getAdvancement();
        NamespacedKey key = advancement.getKey();
        if (key.getKey().startsWith("recipes/")) {
            return;
        }

        Player player = event.getPlayer();
        String title = key.getKey();
        if (title.contains("/")) {
            title = title.substring(title.lastIndexOf('/') + 1);
        }
        title = title.replace('_', ' ');

        String format = plugin.getConfig().getString("advancement.message", "#FFAA00%player% &7получил достижение: &f%advancement%");
        format = format.replace("%player%", player.getName());
        format = format.replace("%displayname%", player.getDisplayName());
        format = format.replace("%advancement%", title);
        format = Text.setPlaceholders(player, format);

        Text.broadcast(format);
    }
}
