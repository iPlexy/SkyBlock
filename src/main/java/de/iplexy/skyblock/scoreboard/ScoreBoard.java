package de.iplexy.skyblock.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class ScoreBoard implements Listener {

    private static Map<UUID, FastBoard> boards = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.RED + "FastBoard");
        boards.put(player.getUniqueId(), board);
        updateLines(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        FastBoard board = boards.remove(player.getUniqueId());
        if (board != null) {
            board.delete();
        }
    }

    public static void updateLines(Player player){
        FastBoard board = boards.get(player.getUniqueId());
        board.updateLines("",
                "Players: " + getServer().getOnlinePlayers().size(),
                "",
                "Kills: " + board.getPlayer().getStatistic(Statistic.PLAYER_KILLS),
                "");
    }
}
