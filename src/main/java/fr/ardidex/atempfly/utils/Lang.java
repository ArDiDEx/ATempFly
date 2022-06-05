package fr.ardidex.atempfly.utils;

import fr.ardidex.atempfly.ATempFly;

import java.util.HashMap;
import java.util.Set;

public class Lang {

    private final HashMap<String, String> langs = new HashMap<>();
    public Lang() {
        // init
        String name = new FileHandler("language").getFileConfig().getString("language");
        new FileHandler("config-en");
        new FileHandler("config-fr");
        assert name != null;
        FileHandler fileHandler = new FileHandler(name.replace(".yml", ""));
        Set<String> keys = fileHandler.getFileConfig().getKeys(false);
        for (String key : keys) {
            langs.put(key, String.valueOf(fileHandler.getFileConfig().get(key)).replace("&", "§"));
        }
    }

    public String parse(String key){
        return langs.get("prefix") + langs.get(key);
    }


    public String get(String key) {
        return langs.get(key);
    }
}
