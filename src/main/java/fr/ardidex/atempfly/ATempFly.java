package fr.ardidex.atempfly;

import fr.ardidex.atempfly.commands.TempFlyCommand;
import fr.ardidex.atempfly.listeners.FactionListener;
import fr.ardidex.atempfly.listeners.PlayerListener;
import fr.ardidex.atempfly.listeners.SuperiorListener;
import fr.ardidex.atempfly.managers.TempFlyManager;
import fr.ardidex.atempfly.placeholders.TimePlaceholder;
import fr.ardidex.atempfly.providers.FactionsProvider;
import fr.ardidex.atempfly.providers.SuperiorProvider;
import fr.ardidex.atempfly.providers.TempFlyProvider;
import fr.ardidex.atempfly.settings.Settings;
import fr.ardidex.atempfly.utils.ConfigHelper;
import fr.ardidex.atempfly.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ATempFly extends JavaPlugin {

    Settings settings;
    ConfigHelper languageConfig;
    TempFlyManager tempFlyManager;
    TempFlyProvider flyProvider;
    @Override
    public void onEnable() {
        // Plugin startup logic
        languageConfig = new ConfigHelper("language",this);
        try {
            settings = new Settings(this);
        }catch (Exception e){
            getLogger().severe("Could not load plugin settings: ");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // fly provider
        if(Bukkit.getServer().getPluginManager().getPlugin("SuperiorSkyblock2") != null){
            flyProvider = new SuperiorProvider(this);
            Bukkit.getPluginManager().registerEvents(new SuperiorListener(this),this);
            getLogger().info("§2Found compatible plugin: SuperiorSkyblock");
        }else {
            try {
                Class.forName("com.massivecraft.factions.Factions");
                flyProvider = new FactionsProvider();
                Bukkit.getPluginManager().registerEvents(new FactionListener(this),this);
                getLogger().info("§2Found compatible plugin: Factions");
            } catch (ClassNotFoundException ignored) {

            }
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null){
            new TimePlaceholder(this).register();
        }
        if(flyProvider == null){
            getLogger().severe("§cNO COMPATIBLE PLUGIN FOUND, DISABLING...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        TimeUtils.setPlugin(this);
        tempFlyManager = new TempFlyManager(this);

        TempFlyCommand tempFlyCommand = new TempFlyCommand(this);
        //noinspection DataFlowIssue
        getCommand("tf").setExecutor(tempFlyCommand);
        //noinspection DataFlowIssue
        getCommand("tf").setTabCompleter(tempFlyCommand);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(tempFlyManager != null)
            tempFlyManager.shutdown();
    }

    public Settings getSettings() {
        return settings;
    }

    public ConfigHelper getLanguageConfig() {
        return languageConfig;
    }

    public TempFlyManager getTempFlyManager() {
        return tempFlyManager;
    }

    public TempFlyProvider getFlyProvider() {
        return flyProvider;
    }
}
