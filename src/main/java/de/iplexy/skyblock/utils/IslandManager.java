package de.iplexy.skyblock.utils;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.exceptions.*;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimeProperties;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import de.iplexy.skyblock.groups.Island;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static de.iplexy.skyblock.SkyBlock.getPlugin;

public class IslandManager {
    SlimePlugin slimePlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
    SlimeLoader loader;

    public IslandManager() {
        loader = slimePlugin.getLoader("file");
    }

    public void createIsland(Island island) {
        String templateName = "Template-" + island.getIslandType().toString().toLowerCase();
        String worldName = "SkyBlock-" + island.getIslandID();
        if (Bukkit.getWorld(templateName) != null) Bukkit.unloadWorld(templateName, true);
        try {
            slimePlugin.importWorld(new File(templateName), worldName, loader);
        } catch (WorldAlreadyExistsException | InvalidWorldException | WorldLoadedException | WorldTooBigException |
                 IOException e) {
            throw new RuntimeException(e);
        }
        SlimePropertyMap properties = new SlimePropertyMap();
        properties.setValue(SlimeProperties.DIFFICULTY, "normal");
        properties.setValue(SlimeProperties.SPAWN_X, 0);
        properties.setValue(SlimeProperties.SPAWN_Y, 67);
        properties.setValue(SlimeProperties.SPAWN_Z, 0);
        try {
            SlimeWorld world = slimePlugin.loadWorld(loader, worldName, false, properties);
            loader.loadWorld(worldName);
            slimePlugin.loadWorld(world);
        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                 WorldLockedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteIsland(Island island) {
        String worldName = "SkyBlock-" + island.getIslandID();
        Bukkit.unloadWorld(worldName, true);
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            try {
                loader.deleteWorld(worldName);
            } catch (IOException | UnknownWorldException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }, 200L);
    }

    private void loadIsland(Island island) {
        String worldName = "SkyBlock-" + island.getIslandID();
        if (Bukkit.getWorld(worldName) == null) {
            try {
                SlimePropertyMap properties = new SlimePropertyMap();
                properties.setValue(SlimeProperties.DIFFICULTY, "normal");
                properties.setValue(SlimeProperties.SPAWN_X, 0);
                properties.setValue(SlimeProperties.SPAWN_Y, 67);
                properties.setValue(SlimeProperties.SPAWN_Z, 0);
                SlimeWorld world = slimePlugin.loadWorld(loader, worldName, false, properties);
                loader.loadWorld(worldName);
                slimePlugin.loadWorld(world);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Bukkit.getWorld(worldName).getWorldBorder().setCenter(Bukkit.getWorld(worldName).getSpawnLocation());
        Bukkit.getWorld(worldName).getWorldBorder().setSize(island.getIslandSize());
    }

    public void teleport(Island island, Player p) {
        loadIsland(island);
        String worldName = "SkyBlock-" + island.getIslandID();
        Location location = Bukkit.getWorld(worldName).getSpawnLocation();
        location.add(0.5, 0, 0.5);
        p.teleport(location);
    }

    public void teleport(String islandID, Player p) {
        teleport(Island.getByID(islandID), p);
    }
}
