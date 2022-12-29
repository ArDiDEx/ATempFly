package fr.ardidex.atempfly.settings;

import fr.ardidex.atempfly.ATempFly;
import fr.ardidex.atempfly.utils.ConfigHelper;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class Settings {
    final Language language;
    final ATempFly plugin;

    public Settings(ATempFly plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getLanguageConfig().getConfig();
        String languageName = config.getString("language") + ".yml";
        if (new File(plugin.getDataFolder(), languageName).exists() || plugin.getClass().getClassLoader().getResource(languageName) != null) {
            language = new Language(new ConfigHelper(languageName, plugin).getConfig());
        } else {
            language = new Language(new ConfigHelper("config-en", plugin).getConfig());
            plugin.getLogger().warning("Could not find language file '" + languageName + "', defaulting to config-en");
        }
    }


    public Language getLanguage() {
        return language;
    }
}
