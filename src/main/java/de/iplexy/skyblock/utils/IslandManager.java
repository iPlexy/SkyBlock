package de.iplexy.skyblock.utils;

import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.*;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class IslandManager {
    SlimePlugin slimePlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
    SlimeLoader loader;

    public IslandManager() {
        loader = slimePlugin.getLoader("mysql");
    }

    public boolean hasOwnIsland(Player p) {
        String worldName = "SkyBlock-" + p.getName();
        if(slimePlugin.getWorld(worldName)==null) return false;
        return true;
    }

    public void createIsland(Player p, String type){
        try {
            String templateName = "Template-" + type;
            String worldName = "SkyBlock-" + p.getName();
            if (Bukkit.getWorld(templateName) != null) Bukkit.unloadWorld(worldName, true);
            slimePlugin.importWorld(new File(templateName), worldName, loader);
            SlimePropertyMap properties = new SlimePropertyMap();
            properties.setValue(SlimeProperties.DIFFICULTY, "normal");
            properties.setValue(SlimeProperties.SPAWN_X, 0);
            properties.setValue(SlimeProperties.SPAWN_Y, 70);
            properties.setValue(SlimeProperties.SPAWN_Z, 0);
            SlimeWorld world = slimePlugin.loadWorld(loader, worldName, false, properties);
            p.teleport(new Location(Bukkit.getWorld(worldName), 0, 0, 0));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteIsland(Player p) {
        String worldName = "SkyBlock-" + p.getName();
        try {
            loader.deleteWorld(worldName);
        } catch (UnknownWorldException | IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
