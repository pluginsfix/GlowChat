package pluginsfix.glowchat.command;

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

public class ChatModeCommand implements CommandExecutor, TabCompleter {
    private final GlowChat plugin;

    public ChatModeCommand(GlowChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        GlowChatConfig config = plugin.getChatConfig();
        GlowChatMessages messages = plugin.getChatMessages();

        if (plugin.getCooldownManager().isOnCooldown(player.getUniqueId(), config.getCommandCooldownMillis())) {
            Text.send(player, messages.getCooldown());
            return true;
        }

        if (!player.hasPermission("glowchat.chatmode")) {
            Text.send(player, messages.getNoPermission());
            return true;
        }

        boolean isGlobal;
        if (plugin.getGlobalChatPlayers().contains(player.getUniqueId())) {
            plugin.getGlobalChatPlayers().remove(player.getUniqueId());
            isGlobal = false;
        } else {
            plugin.getGlobalChatPlayers().add(player.getUniqueId());
            isGlobal = true;
        }

        String modeMessage = isGlobal ? messages.getChatModeGlobal() : messages.getChatModeLocal();
        Text.send(player, modeMessage);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
