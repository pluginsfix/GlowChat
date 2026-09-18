package pluginsfix.glowchat.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pluginsfix.glowchat.GlowChat;
import pluginsfix.glowchat.command.AdminChatCommand;
import pluginsfix.glowchat.command.DonorChatCommand;
import pluginsfix.glowchat.command.ModChatCommand;
import pluginsfix.glowchat.config.GlowChatConfig;
import pluginsfix.glowchat.config.GlowChatMessages;
import pluginsfix.glowchat.util.ColorUtil;
import pluginsfix.glowchat.util.Text;

public class ChatListener implements Listener {
    private final GlowChat plugin;

    public ChatListener(GlowChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        GlowChatConfig config = plugin.getChatConfig();
        GlowChatMessages messages = plugin.getChatMessages();
        if (!config.isChatEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        String message = event.getMessage();

        if (config.isAdminChatEnabled() && config.getAdminChatPrefix() != null && !config.getAdminChatPrefix().isEmpty()) {
            if (message.startsWith(config.getAdminChatPrefix())) {
                if (player.hasPermission("glowchat.adminchat")) {
                    event.setCancelled(true);
                    String chatMsg = message.substring(config.getAdminChatPrefix().length()).trim();
                    if (!chatMsg.isEmpty()) {
                        AdminChatCommand.sendAdminChatMessage(player, chatMsg, config);
                    }
                    return;
                }
            }
        }

        if (config.isModChatEnabled() && config.getModChatPrefix() != null && !config.getModChatPrefix().isEmpty()) {
            if (message.startsWith(config.getModChatPrefix())) {
                if (player.hasPermission("glowchat.modchat") || player.hasPermission("glowchat.adminchat")) {
                    event.setCancelled(true);
                    String chatMsg = message.substring(config.getModChatPrefix().length()).trim();
                    if (!chatMsg.isEmpty()) {
                        ModChatCommand.sendModChatMessage(player, chatMsg, config);
                    }
                    return;
                }
            }
        }

        if (config.isDonorChatEnabled() && config.getDonorChatPrefix() != null && !config.getDonorChatPrefix().isEmpty()) {
            if (message.startsWith(config.getDonorChatPrefix())) {
                if (player.hasPermission("glowchat.donorchat") || player.hasPermission("glowchat.adminchat")) {
                    event.setCancelled(true);
                    String chatMsg = message.substring(config.getDonorChatPrefix().length()).trim();
                    if (!chatMsg.isEmpty()) {
                        DonorChatCommand.sendDonorChatMessage(player, chatMsg, config);
                    }
                    return;
                }
            }
        }

        event.setCancelled(true);
        String globalPrefix = config.getGlobalPrefix();

        boolean localDisabled = config.isLocalDisabled();
        boolean inGlobalMode = plugin.getGlobalChatPlayers().contains(player.getUniqueId());
        boolean hasGlobalPrefix = globalPrefix != null && !globalPrefix.isEmpty() && message.startsWith(globalPrefix);

        if (localDisabled || inGlobalMode || hasGlobalPrefix) {
            if (hasGlobalPrefix) {
                message = message.substring(globalPrefix.length()).trim();
            }
            sendGlobalMessage(player, message, config);
        } else {
            sendLocalMessage(player, message, config, messages);
        }
    }

    private void sendGlobalMessage(Player player, String message, GlowChatConfig config) {
        String format = config.getGlobalFormat();
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

    private void sendLocalMessage(Player player, String message, GlowChatConfig config, GlowChatMessages messages) {
        double radiusSquared = config.getLocalRadiusSquared();
        String format = config.getLocalFormat();
        String color = getPlayerMessageColor(player, config);
        String formattedMessage = format
                .replace("%player%", player.getName())
                .replace("%displayname%", player.getDisplayName())
                .replace("%message%", color + message);

        formattedMessage = Text.setPlaceholders(player, formattedMessage);
        String finalMessage = ColorUtil.colorize(formattedMessage);

        Location playerLoc = player.getLocation();
        int count = 0;
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getWorld().equals(player.getWorld())) {
                if (online.getLocation().distanceSquared(playerLoc) <= radiusSquared) {
                    online.sendMessage(finalMessage);
                    count++;
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage(finalMessage);

        if (count <= 1 && config.isShowNoOneMessage()) {
            Text.send(player, messages.getNoOneHeard());
        }
    }

    private String getPlayerMessageColor(Player player, GlowChatConfig config) {
        if (!config.isMessageColorsEnabled()) {
            return "";
        }
        if (player.hasPermission("glowchat.color.mercury") || player.hasPermission("glowchat.color.gold")) {
            return config.getMercuryColor();
        }
        if (player.hasPermission("glowchat.color.moon") || player.hasPermission("glowchat.color.silver")) {
            return config.getMoonColor();
        }
        if (player.hasPermission("glowchat.color.mars") || player.hasPermission("glowchat.color.bronze")) {
            return config.getMarsColor();
        }
        if (player.hasPermission("glowchat.color.admin")) {
            return config.getAdminColor();
        }
        return config.getDefaultColor();
    }
}
