package de.iplexy.skyblock.commands;

import de.iplexy.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Location spawn = Bukkit.getWorld("SkyBlock").getSpawnLocation();
        spawn.setPitch(2);
        spawn.setYaw(177);
        if(args.length==0){
            if(sender instanceof Player){
                Player p = (Player) sender;
                p.teleport(spawn);
                p.sendMessage("Du wurdest zum Spawn teleportiert.");
            }else{
                SkyBlock.sendConsoleMessage("Dieser Befehl ist nur für Spieler.");
            }
        }else{
            if(args[0].equalsIgnoreCase("all")||args[0].equalsIgnoreCase("*")){
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.teleport(spawn);
                    player.sendMessage("Du wurdest zum Spawn teleportiert.");
                }
            }else if(Bukkit.getPlayerExact(args[0])!=null){
                Player other = Bukkit.getPlayerExact(args[0]);
                other.teleport(spawn);
                other.sendMessage("Du wurdest zum Spawn teleportiert.");
            }else{
                sender.sendMessage("Dieser Spieler ist nicht online.");
            }
        }
        return false;
    }
}
