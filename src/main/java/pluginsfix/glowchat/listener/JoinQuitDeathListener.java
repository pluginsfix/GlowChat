package pluginsfix.glowchat.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pluginsfix.glowchat.GlowChat;
import pluginsfix.glowchat.util.Text;

public class JoinQuitDeathListener implements Listener {
    private final GlowChat plugin;

    public JoinQuitDeathListener(GlowChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!plugin.getConfig().getBoolean("joinquit.enabled", true)) {
            return;
        }

        if (plugin.getConfig().getBoolean("joinquit.disable.join", false)) {
            event.setJoinMessage(null);
            return;
        }

        event.setJoinMessage(null);
        Player player = event.getPlayer();
        String message = plugin.getConfig().getString("joinquit.join", "#55FF55%player% &7зашел на сервер");
        message = message.replace("%player%", player.getName());
        message = message.replace("%displayname%", player.getDisplayName());
        message = Text.setPlaceholders(player, message);
        Text.broadcast(message);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getGlobalChatPlayers().remove(player.getUniqueId());

        if (!plugin.getConfig().getBoolean("joinquit.enabled", true)) {
            return;
        }

        if (plugin.getConfig().getBoolean("joinquit.disable.quit", false)) {
            event.setQuitMessage(null);
            return;
        }

        event.setQuitMessage(null);
        String message = plugin.getConfig().getString("joinquit.quit", "#FF5555%player% &7вышел с сервера");
        message = message.replace("%player%", player.getName());
        message = message.replace("%displayname%", player.getDisplayName());
        message = Text.setPlaceholders(player, message);
        Text.broadcast(message);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!plugin.getConfig().getBoolean("death.enabled", true)) {
            return;
        }

        event.setDeathMessage(null);
        Player player = event.getEntity();
        Player killer = player.getKiller();
        String killerName = killer != null ? killer.getName() : "Unknown";

        if (!plugin.getConfig().getBoolean("death.disable.public", false)) {
            String publicMsg = plugin.getConfig().getString("death.public", "#AAAAAA %player% &7died");
            publicMsg = publicMsg.replace("%player%", player.getName());
            publicMsg = publicMsg.replace("%displayname%", player.getDisplayName());
            publicMsg = publicMsg.replace("%killer%", killerName);
            publicMsg = Text.setPlaceholders(player, publicMsg);
            Text.broadcast(publicMsg);
        }

        if (!plugin.getConfig().getBoolean("death.disable.private", false)) {
            String privateMsg = plugin.getConfig().getString("death.private", "#AAAAAAВы умерли от &f%killer%\n#AAAAAAКоординаты: &f%x%&7, &f%y%&7, &f%z% &7(&f%world%&7)");
            Location loc = player.getLocation();
            privateMsg = privateMsg.replace("%player%", player.getName());
            privateMsg = privateMsg.replace("%displayname%", player.getDisplayName());
            privateMsg = privateMsg.replace("%killer%", killerName);
            privateMsg = privateMsg.replace("%x%", String.valueOf(loc.getBlockX()));
            privateMsg = privateMsg.replace("%y%", String.valueOf(loc.getBlockY()));
            privateMsg = privateMsg.replace("%z%", String.valueOf(loc.getBlockZ()));
            privateMsg = privateMsg.replace("%world%", loc.getWorld() != null ? loc.getWorld().getName() : "");
            privateMsg = Text.setPlaceholders(player, privateMsg);
            Text.send(player, privateMsg);
        }
    }
}
