package fr.ardidex.atempfly.providers;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.island.IslandPrivilege;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import fr.ardidex.atempfly.ATempFly;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SuperiorProvider extends TempFlyProvider{

    private final HashMap<UUID, PermissionAttachment> attachmentList = new HashMap<>();

    @Override
    public boolean setFly(Player player, boolean enabled) {
        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player.getUniqueId());
        Island island = SuperiorSkyblockAPI.getGrid().getIslandAt(superiorPlayer.getLocation());
        if(superiorPlayer.hasIslandFlyEnabled() && !enabled){
            player.setAllowFlight(false);
            player.setFlying(false);
            superiorPlayer.toggleIslandFly();
            PermissionAttachment permissionAttachment = attachmentList.remove(player.getUniqueId());
            permissionAttachment.unsetPermission("superior.island.fly"); // idk if its useful
            player.removeAttachment(permissionAttachment);
            return true;
        }
        else if(island != null && island.hasPermission(superiorPlayer, IslandPrivilege.getByName("FLY")) && !superiorPlayer.hasIslandFlyEnabled() && enabled){
            if(attachmentList.containsKey(player.getUniqueId()))
                player.removeAttachment(attachmentList.remove(player.getUniqueId()));
            player.setAllowFlight(true);
            player.setFlying(true);
            PermissionAttachment permissionAttachment = player.addAttachment(ATempFly.getInstance());
            permissionAttachment.setPermission("superior.island.fly", true);
            superiorPlayer.toggleIslandFly();
            attachmentList.put(player.getUniqueId(), permissionAttachment);
            return true;
        }
        return false;
    }
}
