package com.scoutzknifez.dodgeball.eventhandlers;

import com.scoutzknifez.dodgeball.Main;
import com.scoutzknifez.dodgeball.data.DodgeballGame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinHandler implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage("Welcome to the dodgeball server!");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if (Main.currentGame != null && Main.currentGame.getPlayerTeam(player) != DodgeballGame.Team.NONE)
            Main.currentGame.removePlayer(player);
    }
}
