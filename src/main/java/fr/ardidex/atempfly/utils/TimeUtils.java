package fr.ardidex.atempfly.utils;

import fr.ardidex.atempfly.ATempFly;
import fr.ardidex.atempfly.enums.Time;
import fr.ardidex.atempfly.exceptions.TimeParseException;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {
    static ATempFly plugin;

    public static void setPlugin(ATempFly plugin) {
        TimeUtils.plugin = plugin;
    }

    /**
     * parses string to time
     */
    public static long parseTime(String string) throws TimeParseException {
        Matcher matcher = Pattern.compile("\\d+|\\D+").matcher(string); // https://stackoverflow.com/questions/11232801/regex-split-numbers-and-letter-groups-without-spaces
        long time = 0;
        while (matcher.find())
        {
            String timeString = matcher.group(0);
            long i;
            try{
                i = Long.parseLong(timeString);
            }catch (Exception e){
                throw new TimeParseException();
            }

            boolean b = matcher.find();
            if(!b){
                time += Time.SECONDS.parse(i);
                break;
            }
            String timeUnit = matcher.group(0);
            try {
                time += Time.valueOf(timeUnit.toUpperCase()).parse(i);
            }catch (Exception e){
                throw new TimeParseException();
            }
        }
        return time;
    }

    /**
     * formats a long to a String representing time
     * if uptime is < 0 this will return "Permanent"
     */
    public static String formatTime(long uptime, boolean shortened) {
        if(plugin == null)throw new RuntimeException(new IllegalAccessException("Plugin is not initialized yet"));
        if (uptime <= 0)
            return "§c❌";

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

        String minute = plugin.getSettings().getLanguage().MINUTES;
        String second = plugin.getSettings().getLanguage().SECONDS;
        String hour = plugin.getSettings().getLanguage().HOURS;
        String day = plugin.getSettings().getLanguage().DAYS;
        StringBuilder stringBuilder = new StringBuilder();
        if(shortened){
            if (days != 0)
                stringBuilder.append(days).append(day.charAt(0)).append(" ");
            if (hours != 0)
                stringBuilder.append(hours).append(hour.charAt(0)).append(" ");
            if (minutes != 0)
                stringBuilder.append(minutes).append(minute.charAt(0)).append(" ");
            if (seconds != 0)
                stringBuilder.append(seconds).append(second.charAt(0)).append(" ");
        }else{
            if (days != 0)
                stringBuilder.append(days).append(days > 1 ? day : day.substring(0, day.length() - 1)).append(" ");
            if (hours != 0)
                stringBuilder.append(hours).append(hours > 1 ? hour : hour.substring(0, hour.length() - 1)).append(" ");
            if (minutes != 0)
                stringBuilder.append(minutes).append(minutes > 1 ? minute : minute.substring(0, minute.length() - 1)).append(" ");
            if (seconds != 0)
                stringBuilder.append(seconds).append(seconds > 1 ? second : second.substring(0, second.length() - 1)).append(" ");
        }


        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}
