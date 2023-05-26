package de.iplexy.skyblock.commands;

import de.iplexy.skyblock.SkyBlock;
import de.iplexy.skyblock.groups.Island;
import de.iplexy.skyblock.groups.User;
import de.iplexy.skyblock.utils.IslandManager;
import de.iplexy.skyblock.utils.IslandSerializer;
import de.iplexy.skyblock.utils.IslandType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.nio.Buffer;
import java.util.List;

public class IslandCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p);
        if(args.length>0){
            if(args[0].equalsIgnoreCase("create")){
                if(!user.hasOwnIsland()){
                    p.sendMessage("Island is getting created");
                    Island island = new Island(p, IslandType.NORMAL);
                    IslandSerializer.serialize(island);
                    island.cache();
                    island.printInfo();
                    p.sendMessage("Island created!");
                }else{
                    p.sendMessage("you alr have an island");
                }
            }else if(args[0].equalsIgnoreCase("debug")){
                for(Island island : Island.cachedIslands.values()){
                    island.printInfo();
                }
                for(User users : User.getCachedUsers().values()){
                    users.printInfo();
                }
            }else if(args[0].equalsIgnoreCase("go")||args[0].equalsIgnoreCase("home")){
                if(args.length==1){
                    if(user.hasOwnIsland()){
                        SkyBlock.getIslandManager().teleport(user.getOwnIsland(),p);
                        p.sendMessage("Du wurdest zu deiner Insel teleportiert.");
                    }else{
                        p.sendMessage("Du hast keine eigene Insel.");
                    }
                }else{

                }
            }else if(args[0].equalsIgnoreCase("delete")){
                if(p.getLocation().getWorld().getName().contains("SkyBlock-")){
                    String islandID = p.getLocation().getWorld().getName().replace("SkyBlock-","");
                    Island island = Island.getByID(islandID);
                    if(island.getOwner().equals(p)) {
                        for(Player player : p.getLocation().getWorld().getPlayers()){
                            p.teleport(new Location(Bukkit.getWorld("world"),0,70,0));
                            p.sendMessage("Die Insel wird gelöscht, du wurdest weg teleportiert.");
                        }
                        island.delete();
                        p.sendMessage("Insel gelöscht");
                    }else{
                        p.sendMessage("Diese Insel gehört nicht dir.");
                    }
                }else{
                    p.sendMessage("Du befindest dich nicht auf einer Insel.");
                }
            }else if(args[0].equalsIgnoreCase("info")){
                if(p.getLocation().getWorld().getName().contains("SkyBlock-")){
                    String islandID = p.getLocation().getWorld().getName().replace("SkyBlock-","");
                    Island island = Island.getByID(islandID);
                    p.sendMessage(" §b§m   §r §e"+island.getIslandName()+" §b§m   §r ");
                    p.sendMessage(" §7» §bBesitzer: §e"+island.getOwner().getName());
                    p.sendMessage(" §7» §bMitglieder: ");
                    for(OfflinePlayer member : island.getMembers()){
                        p.sendMessage("   §7- §e"+member.getName());
                    }
                    p.sendMessage(" §7» §bInselgröße: §e"+island.getIslandSize()+"§7x§e"+island.getIslandSize());
                    p.sendMessage(" §7» §bInseltyp: §e"+island.getIslandType());
                }else{
                    p.sendMessage("Du befindest dich nicht auf einer Insel");
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
