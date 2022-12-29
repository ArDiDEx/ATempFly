package fr.ardidex.atempfly.providers;

import org.bukkit.entity.Player;

public abstract class TempFlyProvider {

    public abstract boolean setFly(Player player, boolean enabled);

    // not used
    /*
    public void parseCommand(PlayerCommandPreprocessEvent e){

    };*/
}
