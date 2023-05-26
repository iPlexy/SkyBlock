package de.iplexy.skyblock.groups;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    public static HashMap<OfflinePlayer, User> cachedUsers = new HashMap<>();

    private OfflinePlayer user;
    @Getter
    @Setter
    private Island ownIsland;
    private List<Island> otherIslands;

    public User(OfflinePlayer user) {
        this.user = user;
        this.otherIslands=new ArrayList<>();
        if (!cachedUsers.containsKey(user)) {
            cachedUsers.put(user, this);
        }
    }


    public boolean hasOwnIsland() {
        return ownIsland != null;
    }

    public void addOtherIsland(Island island) {
        if (!otherIslands.contains(island)) {
            otherIslands.add(island);
        }
    }

    public void removeOtherIsland(Island island) {
        if (otherIslands.contains(island)) {
            otherIslands.remove(island);
        }
    }

    public void printInfo() {
        Bukkit.getConsoleSender().sendMessage(" --- " + user.getName() + " --- ");
        Bukkit.getConsoleSender().sendMessage(" » OwnIsland: " + hasOwnIsland());
        ownIsland.printInfo();
        Bukkit.getConsoleSender().sendMessage(" » OtherIslands: " + (otherIslands.size() > 0));
    }


    public static User getUser(OfflinePlayer user) {
        if (cachedUsers.containsKey(user)) {
            return cachedUsers.get(user);
        } else {
            return new User(user);
        }
    }

    public static void deleteCachedUser(User user) {
        cachedUsers.remove(user.user);
    }

    public static HashMap<OfflinePlayer, User> getCachedUsers() {
        return cachedUsers;
    }

    public static void deleteCachedUsers() {
        cachedUsers.clear();
    }

}
