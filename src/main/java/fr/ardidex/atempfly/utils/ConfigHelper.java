package fr.ardidex.atempfly.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigHelper {

    String name;
    Plugin plugin;

    YamlConfiguration config;
    File file;

    public ConfigHelper(String name, Plugin plugin) {
        this.name = name.replace(".yml", "");
        this.plugin = plugin;
        reload();
    }

    public void reload(){
        file = new File(plugin.getDataFolder(),name + ".yml");
        if (!file.exists()) {
            try {
                plugin.saveResource(name + ".yml", true);

                config = YamlConfiguration.loadConfiguration(file);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            config = YamlConfiguration.loadConfiguration(file);
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}