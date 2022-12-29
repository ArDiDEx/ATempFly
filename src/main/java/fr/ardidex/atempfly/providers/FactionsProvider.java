package fr.ardidex.atempfly.providers;

import com.massivecraft.factions.*;
import com.massivecraft.factions.zcore.fperms.Access;
import com.massivecraft.factions.zcore.fperms.PermissableAction;
import org.bukkit.entity.Player;

public class FactionsProvider extends TempFlyProvider{
    @Override
    public boolean setFly(Player player, boolean enabled) {
        FPlayer byPlayer = FPlayers.getInstance().getByPlayer(player);
        Faction faction = Board.getInstance().getFactionAt(new FLocation(player.getLocation()));
        boolean access = faction.getAccess(byPlayer, PermissableAction.FLY) == Access.ALLOW;

        if(!access)return false;
        byPlayer.setFlying(enabled, false);
        return true;
    }
}
