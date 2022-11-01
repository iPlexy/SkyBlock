package de.iplexy.skyblock.commands;

import de.iplexy.skyblock.utils.IslandManager;
import de.iplexy.skyblock.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.iplexy.skyblock.SkyBlock.islandManager;

public class CmdIsland implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command is a player command");
            return true;
        }
        Player p = (Player) sender;
        if (args.length > 0) {
            if (args[0].equals("reload") || args[0].equals("rl")) {
                if (p.hasPermission("SkyBlock")) {
                    Messages.load();
                    sender.sendMessage("Config reloaded");
                } else {
                    sender.sendMessage(Messages.getMessage("nopermissions"));
                }
            } else if (args[0].equals("create")) {
                Bukkit.broadcastMessage("D1");
                if(!islandManager.hasOwnIsland(p)){
                    Bukkit.broadcastMessage("D2");
                    islandManager.createIsland(p, "normal");
                    Bukkit.broadcastMessage("D3");

                }
            }else if (args[0].equals("delete")){
                islandManager.deleteIsland(p);
                p.sendMessage("Deleted.");
            }
        }
        return false;
    }
}
