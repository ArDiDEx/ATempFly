package fr.ardidex.atempfly.managers;

import fr.ardidex.atempfly.ATempFly;
import fr.ardidex.atempfly.objects.TempFly;
import fr.ardidex.atempfly.utils.FileHandler;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import java.util.concurrent.TimeUnit;

public class TempFlyManager {

    private final HashMap<UUID, TempFly> tempFlyPlayers = new HashMap<>();
    private final FileHandler fileHandler = new FileHandler("players");
    public TempFlyManager() {
        new BukkitRunnable() {
            @Override
            public void run() {
                fileHandler.save();
                if(tempFlyPlayers.isEmpty())return;
                tempFlyPlayers.forEach((uuid, tempFly) -> {
                    if(tempFly.getRemaining() <= 0 || tempFly.disabled || !tempFly.getPlayer().getAllowFlight()){
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                disable(tempFly.getPlayer());
                            }
                        }.runTask(ATempFly.getInstance());
                    }
                });
            }
        }.runTaskTimer(ATempFly.getInstance(), 20L, 20L);
    }

    public TempFly disable(Player player){
        TempFly tempFly = tempFlyPlayers.get(player.getUniqueId());
        fileHandler.getFileConfig().set(player.getUniqueId() + "." + "remaining", tempFly.getRemaining());
        tempFlyPlayers.remove(player.getUniqueId());
        ATempFly.getInstance().getFlyProvider().setFly(player, false);
        return tempFly;
    }

    public boolean enable(Player player) throws Exception {
        TempFly tempFly = new TempFly(fileHandler.getFileConfig().getLong(player.getUniqueId() + ".remaining"), player.getUniqueId());
        if(tempFly.getRemaining() <= 0){
            return false;
        }
        if(!ATempFly.getInstance().getFlyProvider().setFly(player, true))throw new Exception("Cannot enable fly.");
        tempFlyPlayers.put(player.getUniqueId(), tempFly);
        return true;
    }

    public boolean isFlying(Player player){
        return tempFlyPlayers.containsKey(player.getUniqueId());
    }

    public Long getRemaining(Player player) {
        return tempFlyPlayers.getOrDefault(player.getUniqueId(), new TempFly(0, null)).getRemaining();
    }

    public Long getCacheRemaining(OfflinePlayer player){
        if(player.isOnline() && isFlying(Objects.requireNonNull(player.getPlayer())))return getRemaining(player.getPlayer());
        return fileHandler.getFileConfig().getLong(player.getUniqueId() + ".remaining");
    }

    public void addTime(OfflinePlayer target, int amount) {
        long remaining = fileHandler.getFileConfig().getLong(target.getUniqueId()+".remaining") + amount;
        fileHandler.getFileConfig().set(target.getUniqueId()+".remaining", remaining);
        if(tempFlyPlayers.containsKey(target.getUniqueId()))
            tempFlyPlayers.get(target.getUniqueId()).setRemaining(remaining);
    }


    public void disableAll() {
        tempFlyPlayers.values().iterator().forEachRemaining((tempFly) -> disable(tempFly.getPlayer()));
    }

    public void removeTime(OfflinePlayer target, int amount) {
        long remaining = Math.max(0,fileHandler.getFileConfig().getLong(target.getUniqueId()+".remaining") - amount);
        fileHandler.getFileConfig().set(target.getUniqueId()+".remaining", remaining);
        if(tempFlyPlayers.containsKey(target.getUniqueId()))
            tempFlyPlayers.get(target.getUniqueId()).setRemaining(remaining);
    }

    public String parseTime(long uptime){
        long days = TimeUnit.MILLISECONDS
                .toDays(uptime);
        uptime -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS
                .toHours(uptime);
        uptime -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS
                .toMinutes(uptime);
        uptime -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS
                .toSeconds(uptime);
        String minute = ATempFly.getInstance().getLang().get("minutes");
        String second = ATempFly.getInstance().getLang().get("seconds");
        String hour = ATempFly.getInstance().getLang().get("hours");
        String day = ATempFly.getInstance().getLang().get("days");
        StringBuilder stringBuilder = new StringBuilder();
        if(days != 0) stringBuilder.append(days).append(days > 1 ? day : day.substring(0,day.length()-1)).append(" ");
        if(hours != 0) stringBuilder.append(hours).append(hours > 1 ? hour : hour.substring(0,hour.length()-1)).append(" ");
        if(minutes != 0) stringBuilder.append(minutes).append(minutes > 1 ? minute : minute.substring(0,minute.length()-1)).append(" ");
        if(seconds != 0) stringBuilder.append(seconds).append(seconds > 1 ? second : second.substring(0,second.length()-1)).append(" ");

        return stringBuilder.substring(0,stringBuilder.length()-1);

    }
    public String parseTimeShort(long uptime){
        long days = TimeUnit.MILLISECONDS
                .toDays(uptime);
        uptime -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS
                .toHours(uptime);
        uptime -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS
                .toMinutes(uptime);
        uptime -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS
                .toSeconds(uptime);
        String minute = ATempFly.getInstance().getLang().get("minutes");
        String second = ATempFly.getInstance().getLang().get("seconds");
        String hour = ATempFly.getInstance().getLang().get("hours");
        String day = ATempFly.getInstance().getLang().get("days");
        StringBuilder stringBuilder = new StringBuilder();
        if(days != 0) stringBuilder.append(days).append(day.charAt(0)).append(" ");
        if(hours != 0) stringBuilder.append(hours).append(hour.charAt(0)).append(" ");
        if(minutes != 0) stringBuilder.append(minutes).append(minute.charAt(0)).append(" ");
        if(seconds != 0) stringBuilder.append(seconds).append(second.charAt(0)).append(" ");
        if(stringBuilder.length() == 0){
            return "§c❌";
        }
        return stringBuilder.substring(0,stringBuilder.length()-1);

    }
}
