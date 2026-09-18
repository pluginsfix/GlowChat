package pluginsfix.glowchat.config;

import org.bukkit.configuration.file.FileConfiguration;

public final class GlowChatMessages {
    private final String reload;
    private final String noPermission;
    private final String usage;
    private final String cooldown;
    private final String chatModeGlobal;
    private final String chatModeLocal;
    private final String noOneHeard;
    private final String adminChatUsage;
    private final String modChatUsage;
    private final String donorChatUsage;

    public GlowChatMessages(FileConfiguration config) {
        this.reload = config.getString("reload");
        this.noPermission = config.getString("no-permission");
        this.usage = config.getString("usage");
        this.cooldown = config.getString("cooldown");
        this.chatModeGlobal = config.getString("chat-mode-global");
        this.chatModeLocal = config.getString("chat-mode-local");
        this.noOneHeard = config.getString("no-one-heard");
        this.adminChatUsage = config.getString("admin-chat-usage");
        this.modChatUsage = config.getString("mod-chat-usage");
        this.donorChatUsage = config.getString("donor-chat-usage");
    }

    public String getReload() {
        return reload;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getUsage() {
        return usage;
    }

    public String getCooldown() {
        return cooldown;
    }

    public String getChatModeGlobal() {
        return chatModeGlobal;
    }

    public String getChatModeLocal() {
        return chatModeLocal;
    }

    public String getNoOneHeard() {
        return noOneHeard;
    }

    public String getAdminChatUsage() {
        return adminChatUsage;
    }

    public String getModChatUsage() {
        return modChatUsage;
    }

    public String getDonorChatUsage() {
        return donorChatUsage;
    }
}
