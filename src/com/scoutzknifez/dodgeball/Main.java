package com.scoutzknifez.dodgeball;

import com.scoutzknifez.dodgeball.commands.DodgeBall;
import com.scoutzknifez.dodgeball.data.DodgeballGame;
import com.scoutzknifez.dodgeball.eventhandlers.JoinHandler;
import com.scoutzknifez.dodgeball.eventhandlers.SnowballHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static DodgeballGame currentGame = null;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Dodgeball plugin enabled!");

        getServer().getPluginManager().registerEvents(new JoinHandler(), this);
        getServer().getPluginManager().registerEvents(new SnowballHandler(), this);

        new DodgeBall(this);
    }

    public static void sendMessageGlobally(String message) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> player.sendMessage(message));
    }
}
