package com.scoutzknifez.dodgeball.commands;

import com.scoutzknifez.dodgeball.Main;
import com.scoutzknifez.dodgeball.data.DodgeballGame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DodgeBall implements CommandExecutor {
    private Main plugin;

    public DodgeBall(Main plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("dodgeball").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0) {
            sendInfo(commandSender);
            return false;
        }
        String action = args[0];

        if (action.equalsIgnoreCase("start")) {
            startGame(commandSender);
        }
        else if (action.equalsIgnoreCase("end")) {
            endGame(commandSender);
        }
        else if (action.equalsIgnoreCase("info")) {
            sendInfo(commandSender);
        }

        return false;
    }

    public void startGame(CommandSender commandSender) {
        if (Main.currentGame != null) {
            commandSender.sendMessage("There is an ongoing game!");
            return;
        }
        Main.currentGame = new DodgeballGame();
        Main.currentGame.startGame();
        commandSender.sendMessage("Game started!");
    }

    public void endGame(CommandSender commandSender) {
        if (Main.currentGame == null) {
            commandSender.sendMessage("No game is started!");
            return;
        }

        Main.currentGame.endGame();
    }

    public void sendInfo(CommandSender commandSender) {
        if (Main.currentGame == null) {
            commandSender.sendMessage("No game is started!");
            return;
        }

        Main.currentGame.getFormattedInfo(commandSender);
    }
}
