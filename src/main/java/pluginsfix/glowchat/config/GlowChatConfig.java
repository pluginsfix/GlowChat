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
    private final boolean showNoOneMessage;
    private final boolean showMessageAnyway;

    private final String globalPrefix;
    private final String globalFormat;

    private final boolean messageColorsEnabled;
    private final String mercuryColor;
    private final String moonColor;
    private final String marsColor;
    private final String adminColor;
    private final String defaultColor;

    private final boolean adminChatEnabled;
    private final String adminChatPrefix;
    private final String adminChatFormat;

    private final boolean modChatEnabled;
    private final String modChatPrefix;
    private final String modChatFormat;

    private final boolean donorChatEnabled;
    private final String donorChatPrefix;
    private final String donorChatFormat;

    private final boolean joinEnabled;
    private final boolean disableJoin;
    private final boolean disableQuit;
    private final String joinFormat;
    private final String quitFormat;

    private final boolean deathEnabled;
    private final boolean disableDeathPublic;
    private final boolean disableDeathPrivate;
    private final String deathPublicFormat;
    private final String deathPrivateFormat;

    private final boolean advancementEnabled;
    private final boolean disableAdvancement;
    private final String advancementFormat;

    private final boolean autoMessagesEnabled;
    private final long autoMessagesIntervalTicks;
    private final List<String> autoMessages;

    private final long commandCooldownMillis;

    public GlowChatConfig(FileConfiguration config) {
        this.chatEnabled = config.getBoolean("chat.enabled");
        this.localDisabled = config.getBoolean("chat.local.disable");
        this.localRadius = config.getDouble("chat.local.radius");
        this.localRadiusSquared = this.localRadius * this.localRadius;
        this.localFormat = config.getString("chat.local.format");
        this.showNoOneMessage = config.getBoolean("chat.local.show-no-one-message");
        this.showMessageAnyway = config.getBoolean("chat.local.show-message-anyway");

        this.globalPrefix = config.getString("chat.global.prefix");
        this.globalFormat = config.getString("chat.global.format");

        this.messageColorsEnabled = config.getBoolean("chat.message-colors.enabled");
        this.mercuryColor = config.getString("chat.message-colors.mercury");
        this.moonColor = config.getString("chat.message-colors.moon");
        this.marsColor = config.getString("chat.message-colors.mars");
        this.adminColor = config.getString("chat.message-colors.admin");
        this.defaultColor = config.getString("chat.message-colors.default");

        this.adminChatEnabled = config.getBoolean("admin-chat.enabled");
        this.adminChatPrefix = config.getString("admin-chat.prefix-symbol");
        this.adminChatFormat = config.getString("admin-chat.format");

        this.modChatEnabled = config.getBoolean("mod-chat.enabled");
        this.modChatPrefix = config.getString("mod-chat.prefix-symbol");
        this.modChatFormat = config.getString("mod-chat.format");

        this.donorChatEnabled = config.getBoolean("donor-chat.enabled");
        this.donorChatPrefix = config.getString("donor-chat.prefix-symbol");
        this.donorChatFormat = config.getString("donor-chat.format");

        this.joinEnabled = config.getBoolean("join-quit.enabled");
        this.disableJoin = config.getBoolean("join-quit.disable-join");
        this.disableQuit = config.getBoolean("join-quit.disable-quit");
        this.joinFormat = config.getString("join-quit.join-format");
        this.quitFormat = config.getString("join-quit.quit-format");

        this.deathEnabled = config.getBoolean("death.enabled");
        this.disableDeathPublic = config.getBoolean("death.disable-public");
        this.disableDeathPrivate = config.getBoolean("death.disable-private");
        this.deathPublicFormat = config.getString("death.public-format");
        this.deathPrivateFormat = config.getString("death.private-format");

        this.advancementEnabled = config.getBoolean("advancement.enabled");
        this.disableAdvancement = config.getBoolean("advancement.disable");
        this.advancementFormat = config.getString("advancement.format");

        this.autoMessagesEnabled = config.getBoolean("auto-messages.enabled");
        long intervalSec = config.getLong("auto-messages.interval-seconds");
        this.autoMessagesIntervalTicks = (intervalSec > 0 ? intervalSec : 300L) * 20L;
        List<String> rawMessages = config.getStringList("auto-messages.messages");
        this.autoMessages = rawMessages != null ? Collections.unmodifiableList(rawMessages) : Collections.emptyList();

        this.commandCooldownMillis = config.getLong("commands.cooldown-milliseconds");
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

    public boolean isModChatEnabled() {
        return modChatEnabled;
    }

    public String getModChatPrefix() {
        return modChatPrefix;
    }

    public String getModChatFormat() {
        return modChatFormat;
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

    public boolean isJoinEnabled() {
        return joinEnabled;
    }

    public boolean isDisableJoin() {
        return disableJoin;
    }

    public boolean isDisableQuit() {
        return disableQuit;
    }

    public String getJoinFormat() {
        return joinFormat;
    }

    public String getQuitFormat() {
        return quitFormat;
    }

    public boolean isDeathEnabled() {
        return deathEnabled;
    }

    public boolean isDisableDeathPublic() {
        return disableDeathPublic;
    }

    public boolean isDisableDeathPrivate() {
        return disableDeathPrivate;
    }

    public String getDeathPublicFormat() {
        return deathPublicFormat;
    }

    public String getDeathPrivateFormat() {
        return deathPrivateFormat;
    }

    public boolean isAdvancementEnabled() {
        return advancementEnabled;
    }

    public boolean isDisableAdvancement() {
        return disableAdvancement;
    }

    public String getAdvancementFormat() {
        return advancementFormat;
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

    public long getCommandCooldownMillis() {
        return commandCooldownMillis;
    }
}
