package de.iplexy.skyblock;


import de.iplexy.skyblock.commands.IslandCommand;
import de.iplexy.skyblock.commands.SpawnCommand;
import de.iplexy.skyblock.groups.Island;
import de.iplexy.skyblock.listeners.JoinListener;
import de.iplexy.skyblock.scoreboard.ScoreBoard;
import de.iplexy.skyblock.utils.IslandManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SkyBlock extends JavaPlugin {
    private static SkyBlock plugin;

    @Getter
    private static IslandManager islandManager;

    @Override
    public void onEnable() {
        plugin = this;
        registerCommands();
        registerListener();
        getDataFolder().mkdir();
        new File(getDataFolder(),"islands").mkdir();
        Island.loadAll();
        islandManager = new IslandManager();
    }

    @Override
    public void onDisable() {
        Island.saveAll();
    }

    private void registerCommands() {
        this.getCommand("Island").setExecutor(new IslandCommand());
        this.getCommand("Spawn").setExecutor(new SpawnCommand());
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new ScoreBoard(), this);
    }

    public static void sendConsoleMessage(String message){
        Bukkit.getConsoleSender().sendMessage("§f[§3SkyBlock§f] §7"+message);
    }


    public static SkyBlock getPlugin() {
        return plugin;
    }
}
