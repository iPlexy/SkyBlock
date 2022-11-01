package de.iplexy.skyblock;

import de.iplexy.skyblock.commands.CmdIsland;
import de.iplexy.skyblock.utils.IslandManager;
import de.iplexy.skyblock.utils.Messages;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyBlock extends JavaPlugin {
    private static SkyBlock plugin;
    public static IslandManager islandManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        registerCommands();
        Messages.load();
        islandManager = new IslandManager();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        this.getCommand("Island").setExecutor(new CmdIsland());
    }


    public static SkyBlock getPlugin() {
        return plugin;
    }
}
