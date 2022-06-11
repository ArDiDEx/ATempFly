package fr.ardidex.atempfly.utils;

import fr.ardidex.atempfly.ATempFly;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileHandler {
    File file;
    FileConfiguration fileConfig;
    private final String fileName;
    public FileHandler(String fileName) {
        this.fileName = fileName;
        load();
    }

    public void reload(){
        load();
    }

    // https://www.spigotmc.org/wiki/config-files/
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void load(){
        file =new File(ATempFly.getInstance().getDataFolder(), fileName + ".yml");
        if(!file.exists())
        {
            try {
                ATempFly.getInstance().saveResource(fileName+".yml", true);
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            }
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        fileConfig = new YamlConfiguration();
        try {
            fileConfig.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save(){
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getFileConfig() {
        return fileConfig;
    }
}
