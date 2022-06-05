package fr.ardidex.atempfly.listeners;

import com.bgsoftware.superiorskyblock.api.events.PlayerToggleFlyEvent;
import fr.ardidex.atempfly.ATempFly;
import fr.ardidex.atempfly.objects.TempFly;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SuperiorListener implements Listener {

    @EventHandler
    public void event(PlayerToggleFlyEvent e){
        Player player = e.getPlayer().asPlayer();
        System.out.println(e.getPlayer().hasIslandFlyEnabled());
        if(e.getPlayer().hasIslandFlyEnabled()) {
            assert player != null;
            if(ATempFly.getInstance().getTempFlyManager().isFlying(player)){
                TempFly disable = ATempFly.getInstance().getTempFlyManager().disable(player);
                player.sendMessage(ATempFly.getInstance().getLang().parse("disable").replace("%time%", ATempFly.getInstance().getTempFlyManager().parseTime(disable.getRemaining())));
            }
        }
    }
}
