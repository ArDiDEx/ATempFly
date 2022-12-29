package fr.ardidex.atempfly.settings;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class Language {
    @NotNull
    public final String PREFIX;
    @NotNull
    public final String PERMISSION;
    @NotNull
    public final String RECEIVE_MESSAGE;
    @NotNull
    public final String LOST_MESSAGE;
    @NotNull
    public final String GIVE_MESSAGE;
    @NotNull
    public final String TAKE_MESSAGE;
    @NotNull
    public final String INFORMATION_MESSAGE;
    @NotNull
    public final String NO_PERMISSION;
    @NotNull
    public final String NO_TEMPFLY;
    @NotNull
    public final String NO_PLAYER;
    @NotNull
    public final String NO_TIME;
    @NotNull
    public final String CORRECT_USAGE;
    @NotNull
    public final String ENABLE;
    @NotNull
    public final String DISABLE;
    @NotNull
    public final String EXPIRED;
    @NotNull
    public final String ERROR_FLY;
    @NotNull
    public final String DAYS;
    @NotNull
    public final String HOURS;
    @NotNull
    public final String MINUTES;
    @NotNull
    public final String SECONDS;
    public Language(FileConfiguration configuration) {
        PREFIX = ChatColor.translateAlternateColorCodes('&', configuration.getString("prefix", "&ea&6TempFly &e» &r"));
        PERMISSION = configuration.getString("permission", "admin.atempfly");
        RECEIVE_MESSAGE = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("receive_message", "&6You have received &e%time% &6of tempfly&f."));
        LOST_MESSAGE = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("lose_message", "&6You have lost &e%time% &6of tempfly&f."));
        GIVE_MESSAGE = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("give_message", "&6You have given &e%time% &6of temp fly to &e%player%&f."));
        TAKE_MESSAGE = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("take_message", "&6You have taken &e%time% &6of temp fly from &e%player%&f."));
        INFORMATION_MESSAGE = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("information_message", "§6%player% has §e%amount%§6 tempfly left§f."));
        NO_PERMISSION = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("no_permission", "&cYou don't have permission&f."));
        NO_TEMPFLY = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("no_tempfly", "&cYou have no more time to fly&f."));
        NO_PLAYER = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("no_player", "&cError player &6%player% &cdoes not exist&f."));
        NO_TIME = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("no_time", "&cError time specified is invalid&f."));
        CORRECT_USAGE = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("correct_usage", "&cUsage: /tf add/remove/get <nickname> [time]&f."));
        ENABLE = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("enable", "&6You have activated your tempfly you have &e%time% &6left&f."));
        DISABLE = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("disable", "6You have deactivated your tempfly you have &e%time% &6left&f."));
        EXPIRED = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("expired", "&cYour fly time has expired&f."));
        ERROR_FLY = PREFIX + ChatColor.translateAlternateColorCodes('&', configuration.getString("error_fly", "&cImpossible to activate the fly in this area&f."));

        DAYS    = configuration.getString("days",    "days");
        HOURS   = configuration.getString("hours",   "hours");
        MINUTES = configuration.getString("minutes", "minutes");
        SECONDS = configuration.getString("seconds", "seconds");
    }
}
