package pluginsfix.glowchat.task;

import java.util.List;
import org.bukkit.scheduler.BukkitRunnable;
import pluginsfix.glowchat.GlowChat;
import pluginsfix.glowchat.config.GlowChatConfig;
import pluginsfix.glowchat.util.Text;

public class AutoMessageTask extends BukkitRunnable {
    private final GlowChat plugin;
    private int currentIndex = 0;

    public AutoMessageTask(GlowChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        GlowChatConfig config = plugin.getChatConfig();
        if (!config.isAutoMessagesEnabled()) {
            return;
        }

        List<String> messages = config.getAutoMessages();
        if (messages == null || messages.isEmpty()) {
            return;
        }

        if (currentIndex >= messages.size()) {
            currentIndex = 0;
        }

        String message = messages.get(currentIndex);
        Text.broadcast(message);
        currentIndex++;
    }
}
