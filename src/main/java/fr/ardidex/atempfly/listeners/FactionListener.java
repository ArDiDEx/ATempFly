package fr.ardidex.atempfly.listeners;

import com.massivecraft.factions.event.FPlayerStoppedFlying;
import fr.ardidex.atempfly.ATempFly;
import fr.ardidex.atempfly.objects.TempFly;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionListener implements Listener {

    @EventHandler
    public void event(FPlayerStoppedFlying e){
        Player player = e.getfPlayer().getPlayer();
        if(ATempFly.getInstance().getTempFlyManager().isFlying(e.getfPlayer().getPlayer())){
            TempFly disable = ATempFly.getInstance().getTempFlyManager().disable(player);
            player.sendMessage(ATempFly.getInstance().getLang().parse("disable").replace("%time%", ATempFly.getInstance().getTempFlyManager().parseTime(disable.getRemaining())));
        }
    }

}
