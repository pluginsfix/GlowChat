package pluginsfix.glowchat.config;

import java.util.Collections;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;

public final class GlowChatConfig {
    private final boolean chatEnabled;
    private final boolean localDisabled;
    private final double localRadius;
    private final double localRadiusSquared;
    private final String localFormat;
    private final String localNoOneMessage;
    private final boolean showNoOneMessage;
    private final boolean showMessageAnyway;

    private final String globalPrefix;
    private final String globalFormat;
    private final String modeSwitchMessage;

    private final boolean messageColorsEnabled;
    private final String mercuryColor;
    private final String moonColor;
    private final String marsColor;
    private final String adminColor;
    private final String defaultColor;

    private final boolean joinEnabled;
    private final boolean joinDisabled;
    private final String joinMessage;

    private final boolean quitEnabled;
    private final boolean quitDisabled;
    private final String quitMessage;

    private final boolean deathEnabled;
    private final boolean deathPublicDisabled;
    private final boolean deathPrivateDisabled;
    private final String deathPublicMessage;
    private final String deathPrivateMessage;

    private final boolean advancementEnabled;
    private final boolean advancementDisabled;
    private final String advancementMessage;

    private final boolean autoMessagesEnabled;
    private final long autoMessagesIntervalTicks;
    private final List<String> autoMessages;

    private final String reloadMessage;
    private final String noPermissionMessage;
    private final String usageMessage;
    private final String cooldownMessage;
    private final long commandCooldownMillis;

    public GlowChatConfig(FileConfiguration config) {
        this.chatEnabled = config.getBoolean("chat.enabled", true);
        this.localDisabled = config.getBoolean("chat.local.disable", false);
        this.localRadius = config.getDouble("chat.local.radius", 100.0);
        this.localRadiusSquared = this.localRadius * this.localRadius;
        this.localFormat = config.getString("chat.local.format", "#AAAAAA[L] &7%displayname%&7: &f%message%");
        this.localNoOneMessage = config.getString("chat.local.noOneMessage", "&7Никто не услышал ваше сообщение");
        this.showNoOneMessage = config.getBoolean("chat.local.showNoOneMessage", true);
        this.showMessageAnyway = config.getBoolean("chat.local.showMessageAnyway", true);

        this.globalPrefix = config.getString("chat.global.prefix", "!");
        this.globalFormat = config.getString("chat.global.format", "#55FFFF[G] &7%displayname%&7: &f%message%");
        this.modeSwitchMessage = config.getString("chat.modeSwitchMessage", "&7Режим чата изменен на: &f%mode%");

        this.messageColorsEnabled = config.getBoolean("chat.messageColors.enabled", true);
        this.mercuryColor = config.getString("chat.messageColors.mercury", "&6");
        this.moonColor = config.getString("chat.messageColors.moon", "&7");
        this.marsColor = config.getString("chat.messageColors.mars", "&c");
        this.adminColor = config.getString("chat.messageColors.admin", "&c");
        this.defaultColor = config.getString("chat.messageColors.default", "&f");

        this.joinEnabled = config.getBoolean("joinquit.enabled", true);
        this.joinDisabled = config.getBoolean("joinquit.disable.join", false);
        this.joinMessage = config.getString("joinquit.join", "#55FF55%player% &7зашел на сервер");

        this.quitEnabled = config.getBoolean("joinquit.enabled", true);
        this.quitDisabled = config.getBoolean("joinquit.disable.quit", false);
        this.quitMessage = config.getString("joinquit.quit", "#FF5555%player% &7вышел с сервера");

        this.deathEnabled = config.getBoolean("death.enabled", true);
        this.deathPublicDisabled = config.getBoolean("death.disable.public", false);
        this.deathPrivateDisabled = config.getBoolean("death.disable.private", false);
        this.deathPublicMessage = config.getString("death.public", "#AAAAAA %player% &7died");
        this.deathPrivateMessage = config.getString("death.private", "#AAAAAAВы умерли от &f%killer%\n#AAAAAAКоординаты: &f%x%&7, &f%y%&7, &f%z% &7(&f%world%&7)");

        this.advancementEnabled = config.getBoolean("advancement.enabled", true);
        this.advancementDisabled = config.getBoolean("advancement.disable", false);
        this.advancementMessage = config.getString("advancement.message", "#FFAA00%player% &7получил достижение: &f%advancement%");

        this.autoMessagesEnabled = config.getBoolean("autoMessages.enabled", true);
        long intervalSec = config.getLong("autoMessages.intervalSeconds", 300L);
        this.autoMessagesIntervalTicks = (intervalSec > 0 ? intervalSec : 300L) * 20L;
        List<String> rawMessages = config.getStringList("autoMessages.messages");
        this.autoMessages = rawMessages != null ? Collections.unmodifiableList(rawMessages) : Collections.emptyList();

        this.reloadMessage = config.getString("messages.reload", "&aКонфигурация GlowChat успешно перезагружена!");
        this.noPermissionMessage = config.getString("messages.no-permission", "&cУ вас нет прав на выполнение этой команды.");
        this.usageMessage = config.getString("messages.usage", "&eGlowChat &7— &f/%command% reload");
        this.cooldownMessage = config.getString("messages.cooldown", "&cПожалуйста, не спамьте командами!");
        this.commandCooldownMillis = config.getLong("commands.cooldown-milliseconds", 1000L);
    }

    public boolean isChatEnabled() {
        return chatEnabled;
    }

    public boolean isLocalDisabled() {
        return localDisabled;
    }

    public double getLocalRadiusSquared() {
        return localRadiusSquared;
    }

    public String getLocalFormat() {
        return localFormat;
    }

    public String getLocalNoOneMessage() {
        return localNoOneMessage;
    }

    public boolean isShowNoOneMessage() {
        return showNoOneMessage;
    }

    public boolean isShowMessageAnyway() {
        return showMessageAnyway;
    }

    public String getGlobalPrefix() {
        return globalPrefix;
    }

    public String getGlobalFormat() {
        return globalFormat;
    }

    public String getModeSwitchMessage() {
        return modeSwitchMessage;
    }

    public boolean isMessageColorsEnabled() {
        return messageColorsEnabled;
    }

    public String getMercuryColor() {
        return mercuryColor;
    }

    public String getMoonColor() {
        return moonColor;
    }

    public String getMarsColor() {
        return marsColor;
    }

    public String getAdminColor() {
        return adminColor;
    }

    public String getDefaultColor() {
        return defaultColor;
    }

    public boolean isJoinEnabled() {
        return joinEnabled;
    }

    public boolean isJoinDisabled() {
        return joinDisabled;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public boolean isQuitEnabled() {
        return quitEnabled;
    }

    public boolean isQuitDisabled() {
        return quitDisabled;
    }

    public String getQuitMessage() {
        return quitMessage;
    }

    public boolean isDeathEnabled() {
        return deathEnabled;
    }

    public boolean isDeathPublicDisabled() {
        return deathPublicDisabled;
    }

    public boolean isDeathPrivateDisabled() {
        return deathPrivateDisabled;
    }

    public String getDeathPublicMessage() {
        return deathPublicMessage;
    }

    public String getDeathPrivateMessage() {
        return deathPrivateMessage;
    }

    public boolean isAdvancementEnabled() {
        return advancementEnabled;
    }

    public boolean isAdvancementDisabled() {
        return advancementDisabled;
    }

    public String getAdvancementMessage() {
        return advancementMessage;
    }

    public boolean isAutoMessagesEnabled() {
        return autoMessagesEnabled;
    }

    public long getAutoMessagesIntervalTicks() {
        return autoMessagesIntervalTicks;
    }

    public List<String> getAutoMessages() {
        return autoMessages;
    }

    public String getReloadMessage() {
        return reloadMessage;
    }

    public String getNoPermissionMessage() {
        return noPermissionMessage;
    }

    public String getUsageMessage() {
        return usageMessage;
    }

    public String getCooldownMessage() {
        return cooldownMessage;
    }

    public long getCommandCooldownMillis() {
        return commandCooldownMillis;
    }
}
