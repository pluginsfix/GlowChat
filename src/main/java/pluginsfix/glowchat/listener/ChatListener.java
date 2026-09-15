package pluginsfix.glowchat.listener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pluginsfix.glowchat.GlowChat;
import pluginsfix.glowchat.util.ColorUtil;
import pluginsfix.glowchat.util.Text;

public class ChatListener implements Listener {
    private final GlowChat plugin;

    public ChatListener(GlowChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("chat.enabled", true)) {
            return;
        }

        event.setCancelled(true);
        Player player = event.getPlayer();
        String message = event.getMessage();
        String globalPrefix = config.getString("chat.global.prefix", "!");

        boolean localDisabled = config.getBoolean("chat.local.disable", false);
        boolean inGlobalMode = plugin.getGlobalChatPlayers().contains(player.getUniqueId());
        boolean hasGlobalPrefix = message.startsWith(globalPrefix);

        if (localDisabled || inGlobalMode || hasGlobalPrefix) {
            if (hasGlobalPrefix) {
                message = message.substring(globalPrefix.length()).trim();
            }
            sendGlobalMessage(player, message, config);
        } else {
            sendLocalMessage(player, message, config);
        }
    }

    private void sendGlobalMessage(Player player, String message, FileConfiguration config) {
        String format = config.getString("chat.global.format", "#55FFFF[G] &7%displayname%&7: &f%message%");
        String color = getPlayerMessageColor(player, config);
        String formattedMessage = format
                .replace("%player%", player.getName())
                .replace("%displayname%", player.getDisplayName())
                .replace("%message%", color + message);

        formattedMessage = Text.setPlaceholders(player, formattedMessage);
        String finalMessage = ColorUtil.colorize(formattedMessage);

        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(finalMessage);
        }
        Bukkit.getConsoleSender().sendMessage(finalMessage);
    }

    private void sendLocalMessage(Player player, String message, FileConfiguration config) {
        double radius = config.getDouble("chat.local.radius", 100.0);
        String format = config.getString("chat.local.format", "#AAAAAA[L] &7%displayname%&7: &f%message%");
        String color = getPlayerMessageColor(player, config);
        String formattedMessage = format
                .replace("%player%", player.getName())
                .replace("%displayname%", player.getDisplayName())
                .replace("%message%", color + message);

        formattedMessage = Text.setPlaceholders(player, formattedMessage);
        String finalMessage = ColorUtil.colorize(formattedMessage);

        int count = 0;
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getWorld().equals(player.getWorld()) && online.getLocation().distance(player.getLocation()) <= radius) {
                online.sendMessage(finalMessage);
                count++;
            }
        }
        Bukkit.getConsoleSender().sendMessage(finalMessage);

        if (count <= 1 && config.getBoolean("chat.local.showNoOneMessage", true)) {
            String noOneMsg = config.getString("chat.local.noOneMessage", "&7Никто не услышал ваше сообщение");
            Text.send(player, noOneMsg);
        }
    }

    private String getPlayerMessageColor(Player player, FileConfiguration config) {
        if (!config.getBoolean("chat.messageColors.enabled", true)) {
            return "";
        }
        if (player.hasPermission("glowchat.color.mercury") || player.hasPermission("glowchat.color.gold")) {
            return config.getString("chat.messageColors.mercury", "&6");
        }
        if (player.hasPermission("glowchat.color.moon") || player.hasPermission("glowchat.color.silver")) {
            return config.getString("chat.messageColors.moon", "&7");
        }
        if (player.hasPermission("glowchat.color.mars") || player.hasPermission("glowchat.color.bronze")) {
            return config.getString("chat.messageColors.mars", "&c");
        }
        if (player.hasPermission("glowchat.color.admin")) {
            return config.getString("chat.messageColors.admin", "&c");
        }
        return config.getString("chat.messageColors.default", "&f");
    }
}
