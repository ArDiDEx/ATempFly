package fr.ardidex.atempfly.placeholders;

import fr.ardidex.atempfly.ATempFly;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TimePlaceholder extends PlaceholderExpansion {
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

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if(params.equalsIgnoreCase("remaining")){
            return ATempFly.getInstance().getTempFlyManager().parseTimeShort(ATempFly.getInstance().getTempFlyManager().getCacheRemaining(player));
        }
        return "";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.equalsIgnoreCase("remaining")){
            return ATempFly.getInstance().getTempFlyManager().parseTimeShort(ATempFly.getInstance().getTempFlyManager().getCacheRemaining(player));
        }
        return "";
    }
}
