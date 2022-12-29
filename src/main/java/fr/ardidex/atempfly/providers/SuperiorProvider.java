package fr.ardidex.atempfly.providers;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.island.IslandPrivilege;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import fr.ardidex.atempfly.ATempFly;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class SuperiorProvider extends TempFlyProvider{
    final ATempFly plugin;
    private final HashMap<UUID, PermissionAttachment> attachmentList = new HashMap<>();

    public SuperiorProvider(ATempFly plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean setFly(Player player, boolean enabled) {
        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player.getUniqueId());
        Island island = SuperiorSkyblockAPI.getGrid().getIslandAt(superiorPlayer.getLocation());
        if(superiorPlayer.hasIslandFlyEnabled() && !enabled){
            player.setAllowFlight(false);
            player.setFlying(false);
            superiorPlayer.toggleIslandFly();
            PermissionAttachment permissionAttachment = attachmentList.remove(player.getUniqueId());
            permissionAttachment.unsetPermission("superior.island.fly"); // idk if it's useful
            player.removeAttachment(permissionAttachment);
            return true;
        }
        else if(island != null && island.hasPermission(superiorPlayer, IslandPrivilege.getByName("FLY")) && !superiorPlayer.hasIslandFlyEnabled() && enabled){
            if(attachmentList.containsKey(player.getUniqueId()))
                player.removeAttachment(attachmentList.remove(player.getUniqueId()));
            player.setAllowFlight(true);
            player.setFlying(true);
            PermissionAttachment permissionAttachment = player.addAttachment(plugin);
            permissionAttachment.setPermission("superior.island.fly", true);
            superiorPlayer.toggleIslandFly();
            attachmentList.put(player.getUniqueId(), permissionAttachment);
            return true;
        }
        return false;
    }
}
