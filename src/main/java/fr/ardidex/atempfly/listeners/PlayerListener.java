package fr.ardidex.atempfly.listeners;

import fr.ardidex.atempfly.ATempFly;
import fr.ardidex.atempfly.objects.TempFly;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void event(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if(ATempFly.getInstance().getTempFlyManager().isFlying(player)){
            TempFly disable = ATempFly.getInstance().getTempFlyManager().disable(player);
            player.sendMessage(ATempFly.getInstance().getLang().parse("disable").replace("%time%", ATempFly.getInstance().getTempFlyManager().parseTime(disable.getRemaining())));
        }
    }

    /*@EventHandler
    public void event(PlayerCommandPreprocessEvent e){
        ATempFly.getInstance().getFlyProvider().parseCommand(e);
    }*/
}
