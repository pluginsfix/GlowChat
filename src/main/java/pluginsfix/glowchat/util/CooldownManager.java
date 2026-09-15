package pluginsfix.glowchat.util;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class CooldownManager {
    private final Map<UUID, Long> cooldowns = new ConcurrentHashMap<>();

    public boolean isOnCooldown(UUID uuid, long cooldownMillis) {
        if (cooldownMillis <= 0) {
            return false;
        }
        long now = System.currentTimeMillis();
        Long lastExecution = cooldowns.get(uuid);
        if (lastExecution == null || (now - lastExecution) >= cooldownMillis) {
            cooldowns.put(uuid, now);
            return false;
        }
        return true;
    }

    public void remove(UUID uuid) {
        cooldowns.remove(uuid);
    }

    public void clear() {
        cooldowns.clear();
    }
}
