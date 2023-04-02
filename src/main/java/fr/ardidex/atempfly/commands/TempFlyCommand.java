package fr.ardidex.atempfly.commands;


import fr.ardidex.atempfly.ATempFly;
import fr.ardidex.atempfly.exceptions.TimeParseException;
import fr.ardidex.atempfly.objects.TempFlyPlayer;
import fr.ardidex.atempfly.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// this is the main command of the plugin
public class TempFlyCommand implements CommandExecutor, TabCompleter {

    private final ATempFly plugin;

    public TempFlyCommand(ATempFly aTempFly) {
        this.plugin = aTempFly;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            if (!sender.hasPermission(plugin.getSettings().getLanguage().PERMISSION)) {
                sender.sendMessage(plugin.getSettings().getLanguage().NO_PERMISSION);
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length < 3) {
                    sender.sendMessage(plugin.getSettings().getLanguage().CORRECT_USAGE);
                    return true;
                }
                List<OfflinePlayer> targets = new ArrayList<>(); // this is required for the "all" target
                if (args[1].equalsIgnoreCase("all")) {
                    targets.addAll(Bukkit.getOnlinePlayers());
                } else {
                    //noinspection deprecation
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    if (!offlinePlayer.hasPlayedBefore()) {
                        sender.sendMessage(plugin.getSettings().getLanguage().NO_PLAYER.replace("%player%", args[1]));
                        return true;
                    }
                    targets.add(offlinePlayer);
                }
                long amount;
                try {
                    amount = TimeUtils.parseTime(args[2]);
                    if (amount <= 0) throw new TimeParseException("Time should be > 0");
                } catch (TimeParseException e) {
                    sender.sendMessage(plugin.getSettings().getLanguage().NO_TIME);
                    return true;
                }
                String s = TimeUtils.formatTime(amount, false);
                for (OfflinePlayer target : targets) {
                    plugin.getTempFlyManager().addTime(target, amount);
                    if (target.isOnline()) {
                        //noinspection DataFlowIssue
                        target.getPlayer().sendMessage(plugin.getSettings().getLanguage().RECEIVE_MESSAGE.replace("%time%", s));
                    }
                }
                if (targets.size() == 1) {
                    //noinspection DataFlowIssue
                    sender.sendMessage(plugin.getSettings().getLanguage().GIVE_MESSAGE.replace("%time%", s)
                            .replace("%player%", targets.get(0).getName()));
                } else {
                    sender.sendMessage(plugin.getSettings().getLanguage().GIVE_MESSAGE.replace("%time%", s)
                            .replace("%player%", targets.size() + "players"));
                }

            } else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length < 3) {
                    sender.sendMessage(plugin.getSettings().getLanguage().CORRECT_USAGE);
                    return true;
                }
                List<OfflinePlayer> targets = new ArrayList<>(); // this is required for the "all" target
                if (args[1].equalsIgnoreCase("all")) {
                    targets.addAll(Bukkit.getOnlinePlayers());
                } else {
                    //noinspection deprecation
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    if (!offlinePlayer.hasPlayedBefore()) {
                        sender.sendMessage(plugin.getSettings().getLanguage().NO_PLAYER.replace("%player%", args[1]));
                        return true;
                    }
                    targets.add(offlinePlayer);
                }
                long amount;
                try {
                    amount = TimeUtils.parseTime(args[2]);
                    if (amount <= 0) throw new TimeParseException("Time should be > 0");
                } catch (TimeParseException e) {
                    sender.sendMessage(plugin.getSettings().getLanguage().NO_TIME);
                    return true;
                }
                String s = TimeUtils.formatTime(amount, false);
                for (OfflinePlayer target : targets) {
                    plugin.getTempFlyManager().removeTime(target, amount);
                    if (target.isOnline()) {
                        //noinspection DataFlowIssue
                        target.getPlayer().sendMessage(plugin.getSettings().getLanguage().LOST_MESSAGE.replace("%time%", s));
                    }
                }
                if (targets.size() == 1) {
                    //noinspection DataFlowIssue
                    sender.sendMessage(plugin.getSettings().getLanguage().TAKE_MESSAGE.replace("%time%", s)
                            .replace("%player%", targets.get(0).getName()));
                } else {
                    sender.sendMessage(plugin.getSettings().getLanguage().TAKE_MESSAGE.replace("%time%", s)
                            .replace("%player%", targets.size() + "players"));
                }
            } else if (args[0].equalsIgnoreCase("get")) {
                if (args.length < 2) {
                    sender.sendMessage(plugin.getSettings().getLanguage().CORRECT_USAGE);
                    return true;
                }
                //noinspection deprecation
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                if (!offlinePlayer.hasPlayedBefore() || offlinePlayer.getName() == null) {
                    sender.sendMessage(plugin.getSettings().getLanguage().NO_PLAYER.replace("%player%", args[1]));
                    return true;
                }
                long time = plugin.getTempFlyManager().getTime(offlinePlayer);

                sender.sendMessage(plugin.getSettings().getLanguage().INFORMATION_MESSAGE
                        .replace("%player%", offlinePlayer.getName())
                        .replace("%time%", TimeUtils.formatTime(time, false)));
            }else{
                sender.sendMessage(plugin.getSettings().getLanguage().CORRECT_USAGE);
            }

            return true;
        }
        if(!(sender instanceof Player)){
            sender.sendMessage(plugin.getSettings().getLanguage().CORRECT_USAGE);
            return true;
        }
        Player player = (Player)sender;
        long time = plugin.getTempFlyManager().getTime(player);
        if(plugin.getTempFlyManager().isFlying(player)){
            TempFlyPlayer flyPlayer = plugin.getTempFlyManager().disable(player);
            player.sendMessage(plugin.getSettings().getLanguage().DISABLE.replace("%time%",TimeUtils.formatTime(time, false)));
            return true;
        }
        if(time <= 0){
            player.sendMessage(plugin.getSettings().getLanguage().NO_TEMPFLY);
            return true;
        }
        if (plugin.getTempFlyManager().enable(player)) {
            player.sendMessage(plugin.getSettings().getLanguage().ENABLE
                    .replace("%time%", TimeUtils.formatTime(time, false)));
        }else{
            player.sendMessage(plugin.getSettings().getLanguage().ERROR_FLY);
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission(plugin.getSettings().getLanguage().PERMISSION))
            return Collections.emptyList();
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("add", "remove", "get"), new ArrayList<>());
        } else if (args.length == 2) {
            List<String> players = Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
            if(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove"))
                players.add("all");
            return StringUtil.copyPartialMatches(args[1], players, new ArrayList<>());
        } else if (args.length == 3 && (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove"))) {
            return StringUtil.copyPartialMatches(args[2], Arrays.asList("<time>"), new ArrayList<>());
        }
        return Collections.emptyList();
    }
}
