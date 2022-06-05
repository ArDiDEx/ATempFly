package fr.ardidex.atempfly.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TempFly {

    public long remaining;
    public long start;
    public boolean disabled = false;
    private final UUID uniqueId;

    public TempFly(long remaining, UUID uniqueId) {
        this.remaining = remaining;
        this.uniqueId = uniqueId;
        start = System.currentTimeMillis();
    }

    public long getRemaining(){
        return remaining-(System.currentTimeMillis()-start);
    }

    public void setRemaining(long remaining) {
        this.remaining = remaining;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uniqueId);
    }

    public UUID getUniqueId() {
        return uniqueId;
    }
}
