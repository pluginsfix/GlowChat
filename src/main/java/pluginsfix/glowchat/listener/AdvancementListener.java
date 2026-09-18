package pluginsfix.glowchat.listener;

import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import pluginsfix.glowchat.GlowChat;
import pluginsfix.glowchat.config.GlowChatConfig;
import pluginsfix.glowchat.util.Text;

public class AdvancementListener implements Listener {
    private final GlowChat plugin;

    public AdvancementListener(GlowChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        GlowChatConfig config = plugin.getChatConfig();
        if (!config.isAdvancementEnabled()) {
            return;
        }
        if (config.isDisableAdvancement()) {
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

        String format = config.getAdvancementFormat();
        format = format.replace("%player%", player.getName());
        format = format.replace("%displayname%", player.getDisplayName());
        format = format.replace("%advancement%", title);
        format = Text.setPlaceholders(player, format);

        Text.broadcast(format);
    }
}
