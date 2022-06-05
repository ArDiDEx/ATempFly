package fr.ardidex.atempfly;


import fr.ardidex.atempfly.managers.TempFlyManager;
import fr.ardidex.atempfly.providers.FactionsProvider;
import fr.ardidex.atempfly.providers.SuperiorProvider;
import fr.ardidex.atempfly.providers.TempFlyProvider;
import fr.ardidex.atempfly.registers.Registration;
import fr.ardidex.atempfly.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class ATempFly extends JavaPlugin {

    private static ATempFly instance;
    private Registration registration;
    private Lang lang;
    private TempFlyManager tempFlyManager;
    private TempFlyProvider flyProvider;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        lang = new Lang();
        tempFlyManager = new TempFlyManager();
        if(Bukkit.getServer().getPluginManager().getPlugin("SuperiorSkyblock2") != null){
            flyProvider = new SuperiorProvider();
            System.out.println("§2Found compatible plugin: SuperiorSkyblock");
        }else {
            try {
                Class.forName("com.massivecraft.factions.Factions");
                flyProvider = new FactionsProvider();
                System.out.println("§2Found compatible plugin: Factions");
            } catch (ClassNotFoundException ignored) {

            }
        }
        if(flyProvider == null){
            System.out.println("§cNO COMPATIBLE PLUGIN FOUND, DISABLING...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }


        registration = new Registration();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(tempFlyManager != null)tempFlyManager.disableAll();
    }

    public static ATempFly getInstance() {
        return instance;
    }

    public Registration getRegistration() {
        return registration;
    }

    public Lang getLang() {
        return lang;
    }

    public TempFlyManager getTempFlyManager() {
        return tempFlyManager;
    }

    public TempFlyProvider getFlyProvider() {
        return flyProvider;
    }
}
