package com.scoutzknifez.dodgeball.eventhandlers;

import com.scoutzknifez.dodgeball.Main;
import com.scoutzknifez.dodgeball.data.DodgeballGame.Team;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class SnowballHandler implements Listener {
    @EventHandler
    public void onSnowballHit(EntityDamageByEntityEvent e) {
        // SAME TEAM CHECK HERE TOO
        if (!(e.getDamager() instanceof Snowball))
            return;

        Snowball snowball = (Snowball) e.getDamager();

        if(!(snowball.getShooter() instanceof Player) || !(e.getEntity() instanceof Player))
            return;

        Player thrower = (Player) snowball.getShooter();
        Player hit = (Player) e.getEntity();

        Team hitTeam = Main.currentGame.getPlayerTeam(hit);
        Team throwerTeam = Main.currentGame.getPlayerTeam(thrower);

        thrower.sendMessage("Hit " + (hitTeam == Team.RED ? ChatColor.RED : ChatColor.BLUE) + hit.getDisplayName());
        hit.sendMessage("Hit By " + (throwerTeam == Team.RED ? ChatColor.RED : ChatColor.BLUE) + thrower.getDisplayName());

        Main.currentGame.eliminatePlayer(hit);

        e.setDamage(0.01);
    }

    @EventHandler
    public void onSnowballThrow(ProjectileLaunchEvent e) {
        if (!(e.getEntity() instanceof Snowball) || !(e.getEntity().getShooter() instanceof  Player))
            return;

        Player thrower = (Player) e.getEntity().getShooter();

        if (Main.currentGame.getPlayerTeam(thrower) == Team.NONE)
            return;

        // AT this point player is throwing a snowball and is on a team
        thrower.getInventory().addItem(new ItemStack(Material.SNOWBALL));
    }
}
