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

    private final boolean adminChatEnabled;
    private final String adminChatPrefix;
    private final String adminChatFormat;
    private final String adminChatUsage;

    private final boolean modChatEnabled;
    private final String modChatPrefix;
    private final String modChatFormat;
    private final String modChatUsage;

    private final boolean donorChatEnabled;
    private final String donorChatPrefix;
    private final String donorChatFormat;
    private final String donorChatUsage;

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
        this.localFormat = config.getString("chat.local.format", "<gray>[L] %displayname%: <white>%message%");
        this.localNoOneMessage = config.getString("chat.local.noOneMessage", "<gray>Никто не услышал ваше сообщение");
        this.showNoOneMessage = config.getBoolean("chat.local.showNoOneMessage", true);
        this.showMessageAnyway = config.getBoolean("chat.local.showMessageAnyway", true);

        this.globalPrefix = config.getString("chat.global.prefix", "!");
        this.globalFormat = config.getString("chat.global.format", "<aqua>[G] <gray>%displayname%: <white>%message%");
        this.modeSwitchMessage = config.getString("chat.modeSwitchMessage", "<gray>Режим чата изменен на: <white>%mode%");

        this.messageColorsEnabled = config.getBoolean("chat.messageColors.enabled", true);
        this.mercuryColor = config.getString("chat.messageColors.mercury", "<gold>");
        this.moonColor = config.getString("chat.messageColors.moon", "<gray>");
        this.marsColor = config.getString("chat.messageColors.mars", "<red>");
        this.adminColor = config.getString("chat.messageColors.admin", "<red>");
        this.defaultColor = config.getString("chat.messageColors.default", "<white>");

        this.adminChatEnabled = config.getBoolean("admin-chat.enabled", true);
        this.adminChatPrefix = config.getString("admin-chat.prefix-symbol", "@");
        this.adminChatFormat = config.getString("admin-chat.format", "<red>[AdminChat] <gray>%displayname%: <white>%message%");
        this.adminChatUsage = config.getString("admin-chat.usage", "<yellow>Использование: <white>/%command% <сообщение>");

        this.modChatEnabled = config.getBoolean("mod-chat.enabled", true);
        this.modChatPrefix = config.getString("mod-chat.prefix-symbol", "?");
        this.modChatFormat = config.getString("mod-chat.format", "<blue>[ModChat] <gray>%displayname%: <white>%message%");
        this.modChatUsage = config.getString("mod-chat.usage", "<yellow>Использование: <white>/%command% <сообщение>");

        this.donorChatEnabled = config.getBoolean("donor-chat.enabled", true);
        this.donorChatPrefix = config.getString("donor-chat.prefix-symbol", "$");
        this.donorChatFormat = config.getString("donor-chat.format", "<gold>[DonorChat] <gray>%displayname%: <white>%message%");
        this.donorChatUsage = config.getString("donor-chat.usage", "<yellow>Использование: <white>/%command% <сообщение>");

        this.joinEnabled = config.getBoolean("joinquit.enabled", true);
        this.joinDisabled = config.getBoolean("joinquit.disable.join", false);
        this.joinMessage = config.getString("joinquit.join", "<green>%player% <gray>зашел на сервер");

        this.quitEnabled = config.getBoolean("joinquit.enabled", true);
        this.quitDisabled = config.getBoolean("joinquit.disable.quit", false);
        this.quitMessage = config.getString("joinquit.quit", "<red>%player% <gray>вышел с сервера");

        this.deathEnabled = config.getBoolean("death.enabled", true);
        this.deathPublicDisabled = config.getBoolean("death.disable.public", false);
        this.deathPrivateDisabled = config.getBoolean("death.disable.private", false);
        this.deathPublicMessage = config.getString("death.public", "<gray>%player% died");
        this.deathPrivateMessage = config.getString("death.private", "<gray>Вы умерли от <white>%killer%\n<gray>Координаты: <white>%x%<gray>, <white>%y%<gray>, <white>%z% <gray>(<white>%world%<gray>)");

        this.advancementEnabled = config.getBoolean("advancement.enabled", true);
        this.advancementDisabled = config.getBoolean("advancement.disable", false);
        this.advancementMessage = config.getString("advancement.message", "<gold>%player% <gray>получил достижение: <white>%advancement%");

        this.autoMessagesEnabled = config.getBoolean("autoMessages.enabled", true);
        long intervalSec = config.getLong("autoMessages.intervalSeconds", 300L);
        this.autoMessagesIntervalTicks = (intervalSec > 0 ? intervalSec : 300L) * 20L;
        List<String> rawMessages = config.getStringList("autoMessages.messages");
        this.autoMessages = rawMessages != null ? Collections.unmodifiableList(rawMessages) : Collections.emptyList();

        this.reloadMessage = config.getString("messages.reload", "<green>Конфигурация GlowChat успешно перезагружена!");
        this.noPermissionMessage = config.getString("messages.no-permission", "<red>У вас нет прав на выполнение этой команды.");
        this.usageMessage = config.getString("messages.usage", "<yellow>GlowChat <gray>— <white>/%command% reload");
        this.cooldownMessage = config.getString("messages.cooldown", "<red>Пожалуйста, не спамьте командами!");
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

    public boolean isAdminChatEnabled() {
        return adminChatEnabled;
    }

    public String getAdminChatPrefix() {
        return adminChatPrefix;
    }

    public String getAdminChatFormat() {
        return adminChatFormat;
    }

    public String getAdminChatUsage() {
        return adminChatUsage;
    }

    public boolean isModChatEnabled() {
        return modChatEnabled;
    }

    public String getModChatPrefix() {
        return modChatPrefix;
    }

    public String getModChatFormat() {
        return modChatFormat;
    }

    public String getModChatUsage() {
        return modChatUsage;
    }

    public boolean isDonorChatEnabled() {
        return donorChatEnabled;
    }

    public String getDonorChatPrefix() {
        return donorChatPrefix;
    }

    public String getDonorChatFormat() {
        return donorChatFormat;
    }

    public String getDonorChatUsage() {
        return donorChatUsage;
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
