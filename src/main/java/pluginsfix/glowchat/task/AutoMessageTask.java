package pluginsfix.glowchat.task;

import java.util.List;
import org.bukkit.scheduler.BukkitRunnable;
import pluginsfix.glowchat.GlowChat;
import pluginsfix.glowchat.util.Text;

public class AutoMessageTask extends BukkitRunnable {
    private final GlowChat plugin;
    private int currentIndex = 0;

    public AutoMessageTask(GlowChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!plugin.getConfig().getBoolean("autoMessages.enabled", true)) {
            return;
        }

        List<String> messages = plugin.getConfig().getStringList("autoMessages.messages");
        if (messages.isEmpty()) {
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
