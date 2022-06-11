package fr.ardidex.atempfly.commands;

import fr.ardidex.atempfly.ATempFly;
import fr.ardidex.atempfly.objects.TempFly;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TempFlyCommand implements CommandExecutor {
    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(args.length > 0 && !sender.hasPermission(ATempFly.getInstance().getLang().get("permission"))){
            sender.sendMessage(ATempFly.getInstance().getLang().parse("no_permission"));
            return true;
        }
        if(args.length > 0){
            // /tf add pseudo [amount]
            switch(args[0]){
                case "add": {
                    List<OfflinePlayer> targets = new ArrayList<>();
                    int amount;
                    if(args.length <= 2){
                        sender.sendMessage(ATempFly.getInstance().getLang().parse("correct_usage"));
                        return true;
                    }
                    if(args[1].equalsIgnoreCase("all")){
                        targets.addAll(Bukkit.getOnlinePlayers());
                    }else
                        targets.add(Bukkit.getOfflinePlayer(args[1]));
                    for (OfflinePlayer target : targets) {
                        if(!target.hasPlayedBefore() && !target.isOnline()){
                            sender.sendMessage(ATempFly.getInstance().getLang().parse("no_player").replace("%player%", args[1]));
                            continue;
                        }
                        try{
                            String time = args[2];
                            if (time.endsWith("days") || time.endsWith("d"))
                                amount = Integer.parseInt(args[2].replace("d", "").replace("days", "")) * 24 * 60 * 60 * 1000;
                            else if (time.endsWith("hours") || time.endsWith("h")) amount = Integer.parseInt(args[2].replace("h", "").replace("hours", "")) * 60 * 60 * 1000;
                            else if (time.endsWith("minutes") || time.endsWith("m")) amount = Integer.parseInt(args[2].replace("m", "").replace("minutes", "")) * 60 * 1000;
                            else if (time.endsWith("seconds") || time.endsWith("s")) amount = Integer.parseInt(args[2].replace("s", "").replace("seconds", "")) * 1000;
                            else
                                amount = Integer.parseInt(time) * 1000;
                        }catch(Exception e){
                            sender.sendMessage(ATempFly.getInstance().getLang().parse("no_time").replace("%time%", args[1]));
                            return true;
                        }
                        ATempFly.getInstance().getTempFlyManager().addTime(target, amount);
                        sender.sendMessage(ATempFly.getInstance().getLang().parse("give_message").replace("%time%", ATempFly.getInstance().getTempFlyManager().parseTime(amount)).replace("%player%", Objects.requireNonNull(target.getName())));
                        if(target.isOnline())
                            Objects.requireNonNull(target.getPlayer()).sendMessage(ATempFly.getInstance().getLang().parse("receive_message").replace("%time%", (amount/1000) + "s"));
                    }
                    return true;
                }
                case "remove": {
                    OfflinePlayer target;
                    int amount;
                    if(args.length <= 2){
                        sender.sendMessage(ATempFly.getInstance().getLang().parse("correct_usage"));
                        return true;
                    }
                    target = Bukkit.getOfflinePlayer(args[1]);
                    if(!target.hasPlayedBefore() && !target.isOnline()){
                        sender.sendMessage(ATempFly.getInstance().getLang().parse("no_player").replace("%player%", args[1]));
                        return true;
                    }
                    try{
                        String time = args[2];
                        if (time.endsWith("days") || time.endsWith("d"))
                            amount = Integer.parseInt(args[2].replace("d", "").replace("days", "")) * 24 * 60 * 60 * 1000;
                        else if (time.endsWith("hours") || time.endsWith("h")) amount = Integer.parseInt(args[2].replace("h", "").replace("hours", "")) * 60 * 60 * 1000;
                        else if (time.endsWith("minutes") || time.endsWith("m")) amount = Integer.parseInt(args[2].replace("m", "").replace("minutes", "")) * 60 * 1000;
                        else if (time.endsWith("seconds") || time.endsWith("s")) amount = Integer.parseInt(args[2].replace("s", "").replace("seconds", "")) * 1000;
                        else
                            amount = Integer.parseInt(time) * 1000;
                    }catch(Exception e){
                        sender.sendMessage(ATempFly.getInstance().getLang().parse("no_time").replace("%time%", args[1]));
                        return true;
                    }
                    ATempFly.getInstance().getTempFlyManager().removeTime(target, amount);
                    sender.sendMessage(ATempFly.getInstance().getLang().parse("give_message").replace("%time%", ATempFly.getInstance().getTempFlyManager().parseTime(amount)).replace("%player%", Objects.requireNonNull(target.getName())));
                    if(target.isOnline())
                        Objects.requireNonNull(target.getPlayer()).sendMessage(ATempFly.getInstance().getLang().parse("receive_message").replace("%time%", (amount/1000) + "s"));
                    return true;
                }
            }
        }
        if(!(sender instanceof Player)){
            sender.sendMessage("§cConsole blocked.");
            return true;
        }
        Player player = (Player)sender;
        if(ATempFly.getInstance().getTempFlyManager().isFlying(player)){
            TempFly disable = ATempFly.getInstance().getTempFlyManager().disable(player);
            player.sendMessage(ATempFly.getInstance().getLang().parse("disable").replace("%time%", ATempFly.getInstance().getTempFlyManager().parseTime(disable.getRemaining())));
        }else{
            try {
                if(ATempFly.getInstance().getTempFlyManager().enable(player)) {
                    player.sendMessage(ATempFly.getInstance().getLang().parse("enable").replace("%time%", ATempFly.getInstance().getTempFlyManager().parseTime(ATempFly.getInstance().getTempFlyManager().getRemaining(player))));
                }else{
                    player.sendMessage(ATempFly.getInstance().getLang().parse("no_tempfly"));
                }
            } catch (Exception e) {
                player.sendMessage(ATempFly.getInstance().getLang().parse("error_fly"));
            }

        }

        return false;
    }
}
