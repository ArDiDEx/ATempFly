package fr.ardidex.atempfly.placeholders;

import fr.ardidex.atempfly.ATempFly;
import fr.ardidex.atempfly.utils.TimeUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TimePlaceholder extends PlaceholderExpansion {
    final ATempFly plugin;

    public TimePlaceholder(ATempFly plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "atempfly";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ardidex";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    /**
     * %atempfly_remaining
     */
    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if(params.equalsIgnoreCase("remaining")){
            return TimeUtils.formatTime(plugin.getTempFlyManager().getTime(player), true);
        }else if(params.equalsIgnoreCase("has_tempfly")){
            return String.valueOf(plugin.getTempFlyManager().getTime(player) >= 1);
        }
        return "ERROR";
    }
}
