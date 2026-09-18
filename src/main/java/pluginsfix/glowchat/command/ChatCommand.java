package pluginsfix.glowchat.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import pluginsfix.glowchat.GlowChat;
import pluginsfix.glowchat.config.GlowChatConfig;
import pluginsfix.glowchat.config.GlowChatMessages;
import pluginsfix.glowchat.util.Text;

public class ChatCommand implements CommandExecutor, TabCompleter {
    private final GlowChat plugin;

    public ChatCommand(GlowChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        GlowChatConfig config = plugin.getChatConfig();
        GlowChatMessages messages = plugin.getChatMessages();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (plugin.getCooldownManager().isOnCooldown(player.getUniqueId(), config.getCommandCooldownMillis())) {
                Text.send(player, messages.getCooldown());
                return true;
            }
        }

        if (args.length == 0) {
            String usageMsg = messages.getUsage().replace("%command%", label);
            Text.send(sender, usageMsg);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("glowchat.reload")) {
                Text.send(sender, messages.getNoPermission());
                return true;
            }

            plugin.reloadPlugin();
            GlowChatMessages updatedMessages = plugin.getChatMessages();
            Text.send(sender, updatedMessages.getReload());
            return true;
        }

        String usageMsg = messages.getUsage().replace("%command%", label);
        Text.send(sender, usageMsg);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1 && sender.hasPermission("glowchat.reload")) {
            List<String> completions = new ArrayList<>();
            if ("reload".startsWith(args[0].toLowerCase())) {
                completions.add("reload");
            }
            return completions;
        }
        return Collections.emptyList();
    }
}
