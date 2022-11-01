package de.iplexy.skyblock.utils;

import de.iplexy.skyblock.SkyBlock;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Messages {
    private static File file = new File(SkyBlock.getPlugin().getDataFolder(), "messages.yml");
    private static YamlConfiguration config;

    public static void load() {
        if (!file.exists()) SkyBlock.getPlugin().saveResource("messages.yml", true);
        config = null;
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static String getMessage(String message) {
        return config.getString("prefix") + config.getString(message);
    }
}
