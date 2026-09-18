package pluginsfix.glowchat.command;

import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import pluginsfix.glowchat.GlowChat;
import pluginsfix.glowchat.config.GlowChatConfig;
import pluginsfix.glowchat.config.GlowChatMessages;
import pluginsfix.glowchat.util.ColorUtil;
import pluginsfix.glowchat.util.Text;

public class AdminChatCommand implements CommandExecutor, TabCompleter {
    private final GlowChat plugin;

    public AdminChatCommand(GlowChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        GlowChatConfig config = plugin.getChatConfig();
        GlowChatMessages messages = plugin.getChatMessages();

        if (!sender.hasPermission("glowchat.adminchat")) {
            Text.send(sender, messages.getNoPermission());
            return true;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (plugin.getCooldownManager().isOnCooldown(player.getUniqueId(), config.getCommandCooldownMillis())) {
                Text.send(player, messages.getCooldown());
                return true;
            }
        }

        if (args.length == 0) {
            String usageMsg = messages.getAdminChatUsage().replace("%command%", label);
            Text.send(sender, usageMsg);
            return true;
        }

        String message = String.join(" ", args);
        sendAdminChatMessage(sender, message, config);
        return true;
    }

    public static void sendAdminChatMessage(CommandSender sender, String message, GlowChatConfig config) {
        String senderName = sender.getName();
        String format = config.getAdminChatFormat()
                .replace("%player%", senderName)
                .replace("%displayname%", sender instanceof Player ? ((Player) sender).getDisplayName() : senderName)
                .replace("%message%", message);

        if (sender instanceof Player) {
            format = Text.setPlaceholders((Player) sender, format);
        }

        String formatted = ColorUtil.colorize(format);

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("glowchat.adminchat")) {
                online.sendMessage(formatted);
            }
        }
        Bukkit.getConsoleSender().sendMessage(formatted);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
