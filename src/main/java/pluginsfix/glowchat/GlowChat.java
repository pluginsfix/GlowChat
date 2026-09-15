package pluginsfix.glowchat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import pluginsfix.glowchat.command.ChatCommand;
import pluginsfix.glowchat.command.ChatModeCommand;
import pluginsfix.glowchat.listener.AdvancementListener;
import pluginsfix.glowchat.listener.ChatListener;
import pluginsfix.glowchat.listener.JoinQuitDeathListener;
import pluginsfix.glowchat.task.AutoMessageTask;

public final class GlowChat extends JavaPlugin {
    private static GlowChat instance;
    private final Set<UUID> globalChatPlayers = new HashSet<>();
    private BukkitTask autoMessageTask;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        registerListeners();
        registerCommands();
        startAutoMessages();
    }

    @Override
    public void onDisable() {
        stopAutoMessages();
        globalChatPlayers.clear();
        instance = null;
    }

    public void reloadPlugin() {
        reloadConfig();
        stopAutoMessages();
        startAutoMessages();
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ChatListener(this), this);
        pm.registerEvents(new JoinQuitDeathListener(this), this);
        pm.registerEvents(new AdvancementListener(this), this);
    }

    private void registerCommands() {
        PluginCommand chatCmd = getCommand("chat");
        if (chatCmd != null) {
            ChatCommand handler = new ChatCommand(this);
            chatCmd.setExecutor(handler);
            chatCmd.setTabCompleter(handler);
        }

        PluginCommand cmCmd = getCommand("chatmode");
        if (cmCmd != null) {
            ChatModeCommand handler = new ChatModeCommand(this);
            cmCmd.setExecutor(handler);
            cmCmd.setTabCompleter(handler);
        }
    }

    private void startAutoMessages() {
        if (!getConfig().getBoolean("autoMessages.enabled", true)) {
            return;
        }

        long intervalTicks = getConfig().getLong("autoMessages.intervalSeconds", 300L) * 20L;
        if (intervalTicks <= 0) {
            intervalTicks = 6000L;
        }

        AutoMessageTask task = new AutoMessageTask(this);
        autoMessageTask = task.runTaskTimer(this, intervalTicks, intervalTicks);
    }

    private void stopAutoMessages() {
        if (autoMessageTask != null) {
            autoMessageTask.cancel();
            autoMessageTask = null;
        }
    }

    public static GlowChat getInstance() {
        return instance;
    }

    public Set<UUID> getGlobalChatPlayers() {
        return globalChatPlayers;
    }
}
