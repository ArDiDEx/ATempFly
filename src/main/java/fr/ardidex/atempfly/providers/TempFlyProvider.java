package fr.ardidex.atempfly.providers;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public abstract class TempFlyProvider {

    public abstract boolean setFly(Player player, boolean enabled);

    // not used
    /*
    public void parseCommand(PlayerCommandPreprocessEvent e){

    };*/
}
