package de.iplexy.skyblock.groups;

import com.google.gson.annotations.Expose;
import de.iplexy.skyblock.SkyBlock;
import de.iplexy.skyblock.utils.IslandManager;
import de.iplexy.skyblock.utils.IslandSerializer;
import de.iplexy.skyblock.utils.IslandType;
import de.iplexy.skyblock.utils.RandomString;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static de.iplexy.skyblock.SkyBlock.getPlugin;

public class Island {

    public static HashMap<String, Island> cachedIslands = new HashMap<>(); //IslandID, Island

    @Expose
    @Getter
    private OfflinePlayer owner;
    @Expose
    @Getter
    private List<OfflinePlayer> members;
    @Expose
    @Getter
    private int islandSize;
    @Expose
    @Getter
    private String islandName;
    @Expose
    @Getter
    private IslandType islandType;
    @Expose
    @Getter
    private String islandID;

    public Island(){
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            updateUsers();
        }, 10L);
    }

    public Island(OfflinePlayer owner, IslandType islandType){
        this.owner = owner;
        this.members = new ArrayList<>();
        this.islandSize=16;
        this.islandName=owner.getName()+"'s Insel";
        this.islandID=newID();
        this.islandType=islandType;
        User.getUser(owner).setOwnIsland(this);
        SkyBlock.getIslandManager().createIsland(this);
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            updateUsers();
        }, 10L);
    }

    public void printInfo(){
        Bukkit.getConsoleSender().sendMessage(" --- "+islandName+" --- ");
        Bukkit.getConsoleSender().sendMessage(" » Owner: "+owner.getName());
        Bukkit.getConsoleSender().sendMessage(" » Member: "+members);
        Bukkit.getConsoleSender().sendMessage(" » IslandSize: "+islandSize);
        Bukkit.getConsoleSender().sendMessage(" » IslandName: "+islandName);
        Bukkit.getConsoleSender().sendMessage(" » IslandID: "+islandID);
        Bukkit.getConsoleSender().sendMessage(" » IslandType: "+islandType+"\n");
    }


    public void cache(){
        if(!cachedIslands.containsValue(this)){
            cachedIslands.put(this.islandID,this);
        }
    }

    public void updateUsers(){
        User.getUser(owner).setOwnIsland(this);
        if(members!=null) {
            for (OfflinePlayer member : this.members) {
                User.getUser(member).addOtherIsland(this);
            }
        }
    }

    public void delete(){
        User.getUser(this.owner).setOwnIsland(null);
        for(OfflinePlayer player : this.getMembers()){
            User.getUser(player).removeOtherIsland(this);
        }
        SkyBlock.getIslandManager().deleteIsland(this);
        cachedIslands.remove(this.islandID);
        new File(getPlugin().getDataFolder() + "/islands/", this.getIslandID() + ".json").delete();
    }





    public static void saveAll(){
        for(Island island : cachedIslands.values()){
            IslandSerializer.serialize(island);
        }
    }

    public static void loadAll(){
        File folder = new File(getPlugin().getDataFolder() + "/islands/");
        if (folder.listFiles() == null) return;
        for (File f : folder.listFiles()) {
            IslandSerializer.deserialize(f);
        }
    }

    private static String newID() {
        String uid = RandomString.get(16);
        File file = new File(getPlugin().getDataFolder() + "/islands/", uid + ".json");
        while (file.exists()) {
            uid = RandomString.get(16);
            file = new File(getPlugin().getDataFolder() + "/islands/", uid + ".json");
        }
        return uid;
    }

    public static Island getByID(String ID){
        return cachedIslands.get(ID);
    }
}
