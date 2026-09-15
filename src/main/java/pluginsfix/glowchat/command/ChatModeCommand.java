package pluginsfix.glowchat.command;

import java.util.Collections;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import pluginsfix.glowchat.GlowChat;
import pluginsfix.glowchat.util.Text;

public class ChatModeCommand implements CommandExecutor, TabCompleter {
    private final GlowChat plugin;

    public ChatModeCommand(GlowChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("glowchat.chatmode")) {
            String noPermMsg = plugin.getConfig().getString("messages.no-permission", "&cУ вас нет прав на выполнение этой команды.");
            Text.send(player, noPermMsg);
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

        String modeName = isGlobal ? "Глобальный" : "Локальный";
        String modeSwitchMsg = plugin.getConfig().getString("chat.modeSwitchMessage", "&7Режим чата изменен на: &f%mode%");
        modeSwitchMsg = modeSwitchMsg.replace("%mode%", modeName);
        Text.send(player, modeSwitchMsg);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
