package fr.ardidex.atempfly.providers;

import com.massivecraft.factions.*;
import com.massivecraft.factions.perms.PermissibleAction;
import com.massivecraft.factions.zcore.fperms.Access;
import com.massivecraft.factions.zcore.fperms.PermissableAction;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FactionsProvider extends TempFlyProvider{
    boolean isSaberFaction = false;

    public FactionsProvider() {
        try {
            Class.forName("pw.saber.corex.CoreX");
            this.isSaberFaction = true;
        }catch (Exception e){
            this.isSaberFaction = false;
        }
    }

    @Override
    public boolean setFly(Player player, boolean enabled) {
        FPlayer byPlayer = FPlayers.getInstance().getByPlayer(player);
        Faction faction = Board.getInstance().getFactionAt(new FLocation(player.getLocation()));
        boolean access;
        if(isSaberFaction){
            try {
                Method method = Faction.class.getDeclaredMethod("getAccess", FPlayer.class, PermissableAction.class);
                Access acc = (Access) method.invoke(faction, byPlayer, PermissableAction.FLY);
                access = acc.equals(Access.ALLOW);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

        }else{
            try {
                Method method = Faction.class.getDeclaredMethod("hasAccess", FPlayer.class, PermissibleAction.class);
                access = (boolean) method.invoke(faction, byPlayer, PermissibleAction.FLY);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        if(!access)return false;
        byPlayer.setFlying(enabled, false);
        return true;
    }

}
