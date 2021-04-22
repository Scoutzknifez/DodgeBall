package com.scoutzknifez.dodgeball.data;

import com.scoutzknifez.dodgeball.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class DodgeballGame {
    private ArrayList<Player> redTeamPlayers = new ArrayList<>();
    private ArrayList<Player> blueTeamPlayers = new ArrayList<>();

    private int redScore = 0;
    private int blueScore = 0;

    public void startGame() {
        Bukkit.getServer().getOnlinePlayers().forEach(this::addPlayer);
    }

    public void endGame() {
        redTeamPlayers.forEach(player -> {
            player.sendMessage("Game has ended!");
        });
        redTeamPlayers.clear();

        blueTeamPlayers.forEach(player -> {
            player.sendMessage("Game has ended!");
        });
        blueTeamPlayers.clear();

        Main.currentGame = null;
    }

    public void addPlayer(Player player) {
        ArrayList<Player> addToList = redTeamPlayers.size() > blueTeamPlayers.size() ? blueTeamPlayers : redTeamPlayers;
        addToList.add(player);

        player.sendMessage(
                (getPlayerTeam(player) == Team.RED ?
                        ChatColor.RED + "" + ChatColor.BOLD + "RED TEAM" :
                        ChatColor.BLUE + "" + ChatColor.BOLD + "BLUE TEAM")
        );

        player.getInventory().addItem(new ItemStack(Material.SNOWBALL, 8));

        Location redSpawn = new Location(player.getWorld(), -155, 107, -253);
        Location blueSpawn = new Location(player.getWorld(), -137, 107, -253);
        player.teleport(getPlayerTeam(player) == Team.RED ? redSpawn : blueSpawn);
    }

    public void removePlayer(Player player) {
        boolean redTeamHadPlayer = redTeamPlayers.remove(player);

        if (!redTeamHadPlayer) {
            blueTeamPlayers.remove(player);
        }
    }

    public void eliminatePlayer(Player player) {
        removePlayer(player);
        player.teleport(player.getWorld().getSpawnLocation());

        if (redTeamPlayers.size() == 0) {
            Main.sendMessageGlobally(ChatColor.BLUE + "Blue team wins!");
            blueScore++;
        }
        else if (blueTeamPlayers.size() == 0) {
            Main.sendMessageGlobally(ChatColor.RED + "Red team wins!");
            redScore++;
        }

        Main.sendMessageGlobally(ChatColor.RED + "" + ChatColor.BOLD + redScore + ChatColor.RESET + " - " + ChatColor.BLUE + "" + ChatColor.BOLD + blueScore);
        resetGame();
    }

    public void resetGame() {
        Bukkit.getOnlinePlayers().forEach(player -> player.getWorld().setTime(6000));

        redTeamPlayers.clear();
        blueTeamPlayers.clear();

        startGame();
    }

    public Team getPlayerTeam(Player player) {
        if (redTeamPlayers.contains(player))
            return Team.RED;
        else if (blueTeamPlayers.contains(player))
            return Team.BLUE;
        else
            return Team.NONE;
    }

    public void getFormattedInfo(CommandSender sender) {
        sender.sendMessage(ChatColor.BOLD + "GAME INFO");
        sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + redScore + ChatColor.RESET + " - " + ChatColor.BLUE + "" + ChatColor.BOLD + blueScore);
        sender.sendMessage("");

        sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "RED TEAM");
        String redPlayerList = ChatColor.RED + "";
        for (Player player: redTeamPlayers) {
            redPlayerList += player.getDisplayName();
        }
        sender.sendMessage(redPlayerList);
        sender.sendMessage("");

        sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "BLUE TEAM");
        String bluePlayerList = ChatColor.BLUE + "";
        for (Player player: blueTeamPlayers) {
            bluePlayerList += player.getDisplayName();
        }
        sender.sendMessage(bluePlayerList);
    }

    public enum Team {
        RED,
        BLUE,
        NONE
    }
}
