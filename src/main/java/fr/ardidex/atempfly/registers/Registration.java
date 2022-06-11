package fr.ardidex.atempfly.registers;

import fr.ardidex.atempfly.ATempFly;
import fr.ardidex.atempfly.commands.TempFlyCommand;
import fr.ardidex.atempfly.listeners.FactionListener;
import fr.ardidex.atempfly.listeners.PlayerListener;
import fr.ardidex.atempfly.listeners.SuperiorListener;
import fr.ardidex.atempfly.placeholders.TimePlaceholder;
import fr.ardidex.atempfly.providers.FactionsProvider;
import fr.ardidex.atempfly.providers.SuperiorProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Registration {
    public Registration() {
        PluginManager pm = Bukkit.getPluginManager();
        ATempFly instance = ATempFly.getInstance();
        if(instance.getFlyProvider().getClass().equals(FactionsProvider.class))
            pm.registerEvents(new FactionListener(), instance);
        else if(instance.getFlyProvider().getClass().equals(SuperiorProvider.class))
            pm.registerEvents(new SuperiorListener(), instance);
        pm.registerEvents(new PlayerListener(), instance);
        instance.getCommand("tf").setExecutor(new TempFlyCommand());
        new TimePlaceholder().register();
    }
}
