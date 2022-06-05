package fr.ardidex.atempfly.providers;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import org.bukkit.entity.Player;

public class FactionsProvider extends TempFlyProvider{
    @Override
    public boolean setFly(Player player, boolean enabled) {
        FPlayer byPlayer = FPlayers.getInstance().getByPlayer(player);
        if(!byPlayer.canFlyAtLocation())return false;
        byPlayer.setFlying(enabled, false);
        return true;
    }
}
