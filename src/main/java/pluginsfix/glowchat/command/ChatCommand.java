package pluginsfix.glowchat.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import pluginsfix.glowchat.GlowChat;
import pluginsfix.glowchat.util.Text;

public class ChatCommand implements CommandExecutor, TabCompleter {
    private final GlowChat plugin;

    public ChatCommand(GlowChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            String usageMsg = plugin.getConfig().getString("messages.usage", "&eGlowChat &7— &f/%command% reload");
            usageMsg = usageMsg.replace("%command%", label);
            Text.send(sender, usageMsg);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("glowchat.reload")) {
                String noPermMsg = plugin.getConfig().getString("messages.no-permission", "&cУ вас нет прав на выполнение этой команды.");
                Text.send(sender, noPermMsg);
                return true;
            }

            plugin.reloadPlugin();
            String reloadMsg = plugin.getConfig().getString("messages.reload", "&aКонфигурация GlowChat успешно перезагружена!");
            Text.send(sender, reloadMsg);
            return true;
        }

        String usageMsg = plugin.getConfig().getString("messages.usage", "&eGlowChat &7— &f/%command% reload");
        usageMsg = usageMsg.replace("%command%", label);
        Text.send(sender, usageMsg);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            if ("reload".startsWith(args[0].toLowerCase())) {
                completions.add("reload");
            }
            return completions;
        }
        return Collections.emptyList();
    }
}
