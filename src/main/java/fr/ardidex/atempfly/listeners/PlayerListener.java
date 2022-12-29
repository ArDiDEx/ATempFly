package fr.ardidex.atempfly.listeners;

import fr.ardidex.atempfly.ATempFly;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final ATempFly plugin;

    public PlayerListener(ATempFly plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void event(PlayerQuitEvent e){
        if(plugin.getTempFlyManager().isFlying(e.getPlayer())){
            plugin.getTempFlyManager().disable(e.getPlayer());
        }
    }
}
