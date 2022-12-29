package fr.ardidex.atempfly.listeners;

import com.bgsoftware.superiorskyblock.api.events.PlayerToggleFlyEvent;
import fr.ardidex.atempfly.ATempFly;
import fr.ardidex.atempfly.utils.TimeUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SuperiorListener implements Listener {
    final ATempFly plugin;

    public SuperiorListener(ATempFly plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void event(PlayerToggleFlyEvent e){
        Player player = e.getPlayer().asPlayer();
        if(!e.getPlayer().hasIslandFlyEnabled())return;
        if(player == null)return;

        if(plugin.getTempFlyManager().isFlying(player)){
            // player is flying through ATempFly
            plugin.getTempFlyManager().disable(player);
            player.sendMessage(plugin.getSettings().getLanguage().DISABLE
                    .replace("%time%", TimeUtils.formatTime(plugin.getTempFlyManager().getTime(player), false)));

        }
    }
}
