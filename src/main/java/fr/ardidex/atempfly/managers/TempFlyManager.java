package fr.ardidex.atempfly.managers;

import fr.ardidex.atempfly.ATempFly;
import fr.ardidex.atempfly.objects.TempFlyPlayer;
import fr.ardidex.atempfly.utils.ConfigHelper;
import fr.ardidex.atempfly.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TempFlyManager {
    final ATempFly plugin;
    final HashMap<UUID, TempFlyPlayer> tempFlyPlayers = new HashMap<>();
    final ConfigHelper playersConfig;
    final BukkitTask flyTask;
    final BukkitTask saveTask;

    public TempFlyManager(ATempFly plugin) {
        this.plugin = plugin;
        playersConfig = new ConfigHelper("players", plugin);
        flyTask = new BukkitRunnable() {
            @Override
            public void run() {
                List<Player> toDisable = new ArrayList<>();
                tempFlyPlayers.forEach((uuid, tempFlyPlayer) -> {
                    Player player = Bukkit.getPlayer(uuid);
                    if (player == null) {
                        return;
                    }

                    long remaining = tempFlyPlayer.getRemaining();
                    if (remaining <= 0 || !player.getAllowFlight()) {
                        toDisable.add(player);
                    }
                });
                toDisable.forEach(player -> {
                    disable(player);
                    player.sendMessage(plugin.getSettings().getLanguage().DISABLE
                            .replace("%time%", TimeUtils.formatTime(plugin.getTempFlyManager().getTime(player), false)));

                });
            }
        }.runTaskTimer(plugin, 20L, 20L);
        saveTask = new BukkitRunnable() {
            @Override
            public void run() {
                playersConfig.saveConfig();
            }
        }.runTaskTimer(plugin, 20 * 60L, 20 * 60L);
    }

    public boolean enable(Player target) {
        if (isFlying(target)) return false;
        long remaining = playersConfig.getConfig().getLong(target.getUniqueId() + ".remaining", -1);
        if (remaining <= 0) {
            return false;
        }
        if (!plugin.getFlyProvider().setFly(target, true)) {
            return false;
        }

        tempFlyPlayers.put(target.getUniqueId(), new TempFlyPlayer(remaining));
        return true;
    }

    @SuppressWarnings("UnusedReturnValue")
    public TempFlyPlayer disable(Player target) {
        if (!isFlying(target)) return null;
        TempFlyPlayer tempFlyPlayer = tempFlyPlayers.remove(target.getUniqueId());
        long remaining = tempFlyPlayer.getRemaining();
        plugin.getFlyProvider().setFly(target,false);
        playersConfig.getConfig().set(target.getUniqueId() + ".remaining", Math.max(remaining, 0));

        return tempFlyPlayer;
    }

    public void addTime(OfflinePlayer target, long amount) {
        long newTime;
        try {
            newTime = Math.addExact(getTime(target), amount);
        } catch (ArithmeticException ignored) {
            newTime = Long.MAX_VALUE;
        }

        if (tempFlyPlayers.containsKey(target.getUniqueId())) {
            tempFlyPlayers.get(target.getUniqueId()).setRemaining(newTime);
        }

        playersConfig.getConfig().set(target.getUniqueId() + ".remaining", newTime);
    }

    public void removeTime(OfflinePlayer target, long amount) {
        long newTime;
        try {
            newTime = Math.subtractExact(getTime(target), amount);
            if (newTime < 0)
                newTime = 0;
        } catch (ArithmeticException ignored) {
            newTime = 0;
        }

        if (tempFlyPlayers.containsKey(target.getUniqueId())) {
            tempFlyPlayers.get(target.getUniqueId()).setRemaining(newTime);
        }

        playersConfig.getConfig().set(target.getUniqueId() + ".remaining", newTime);
    }

    public long getTime(OfflinePlayer target) {
        if (tempFlyPlayers.containsKey(target.getUniqueId())) {
            return tempFlyPlayers.get(target.getUniqueId()).getRemaining();
        }

        return playersConfig.getConfig().getLong(target.getUniqueId() + ".remaining", 0);
    }

    public boolean isFlying(OfflinePlayer target) {
        return tempFlyPlayers.containsKey(target.getUniqueId());
    }

    public void shutdown() {
        saveTask.cancel();
        flyTask.cancel();
        for (UUID uuid : tempFlyPlayers.keySet().stream().toList()) {
            disable(Bukkit.getPlayer(uuid));
        }
        playersConfig.saveConfig();
    }
}
