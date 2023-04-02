package fr.ardidex.atempfly.listeners;

import com.massivecraft.factions.event.FPlayerStoppedFlying;
import fr.ardidex.atempfly.ATempFly;
import fr.ardidex.atempfly.utils.TimeUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionListener implements Listener {
    final ATempFly plugin;

    public FactionListener(ATempFly plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void event(FPlayerStoppedFlying e){
        Player player = e.getfPlayer().getPlayer();
        if(plugin.getTempFlyManager().isFlying(player)){
            // player is flying through ATempFly
            plugin.getTempFlyManager().disable(player);
            player.sendMessage(plugin.getSettings().getLanguage().DISABLE
                    .replace("%time%", TimeUtils.formatTime(plugin.getTempFlyManager().getTime(player), false)));

        }

    }
}
