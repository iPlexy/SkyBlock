package de.iplexy.skyblock.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.iplexy.skyblock.groups.Island;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.*;

import static de.iplexy.skyblock.SkyBlock.getPlugin;

public class IslandSerializer {
    public static void serialize(Island island) {
        File file = new File(getPlugin().getDataFolder() + "/islands/", island.getIslandID() + ".json");
        try {
            if (!file.exists()) file.createNewFile();
            file.mkdirs();
            Writer writer = new FileWriter(file);
            Gson gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(ConfigurationSerializable.class, new ConfigurationSerializableAdapter())
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            gson.toJson(island, writer);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void deserialize(File file){
        try {
            if (!file.exists()) file.createNewFile();
            Reader reader = new FileReader(file);
            Gson gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(ConfigurationSerializable.class, new ConfigurationSerializableAdapter())
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            Island island = gson.fromJson(reader, Island.class);
            island.cache();
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
