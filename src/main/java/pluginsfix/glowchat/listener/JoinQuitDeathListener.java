package pluginsfix.glowchat.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pluginsfix.glowchat.GlowChat;
import pluginsfix.glowchat.config.GlowChatConfig;
import pluginsfix.glowchat.util.Text;

public class JoinQuitDeathListener implements Listener {
    private final GlowChat plugin;

    public JoinQuitDeathListener(GlowChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        GlowChatConfig config = plugin.getChatConfig();
        if (!config.isJoinEnabled()) {
            return;
        }

        if (config.isDisableJoin()) {
            event.setJoinMessage(null);
            return;
        }

        event.setJoinMessage(null);
        Player player = event.getPlayer();
        String message = config.getJoinFormat();
        message = message.replace("%player%", player.getName());
        message = message.replace("%displayname%", player.getDisplayName());
        message = Text.setPlaceholders(player, message);
        Text.broadcast(message);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getGlobalChatPlayers().remove(player.getUniqueId());
        plugin.getCooldownManager().remove(player.getUniqueId());

        GlowChatConfig config = plugin.getChatConfig();
        if (!config.isQuitEnabled()) {
            return;
        }

        if (config.isDisableQuit()) {
            event.setQuitMessage(null);
            return;
        }

        event.setQuitMessage(null);
        String message = config.getQuitFormat();
        message = message.replace("%player%", player.getName());
        message = message.replace("%displayname%", player.getDisplayName());
        message = Text.setPlaceholders(player, message);
        Text.broadcast(message);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        GlowChatConfig config = plugin.getChatConfig();
        if (!config.isDeathEnabled()) {
            return;
        }

        event.setDeathMessage(null);
        Player player = event.getEntity();
        Player killer = player.getKiller();
        String killerName = killer != null ? killer.getName() : "";

        if (!config.isDisableDeathPublic()) {
            String publicMsg = config.getDeathPublicFormat();
            publicMsg = publicMsg.replace("%player%", player.getName());
            publicMsg = publicMsg.replace("%displayname%", player.getDisplayName());
            publicMsg = publicMsg.replace("%killer%", killerName);
            publicMsg = Text.setPlaceholders(player, publicMsg);
            Text.broadcast(publicMsg);
        }

        if (!config.isDisableDeathPrivate()) {
            String privateMsg = config.getDeathPrivateFormat();
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
