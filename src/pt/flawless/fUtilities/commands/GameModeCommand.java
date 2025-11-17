package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.helpers.GameModeHelper;
import pt.flawless.fUtilities.managers.MessagesManager;
import pt.flawless.fapi.sounds.FSound;

public class GameModeCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if (args.length == 0) {
            p.sendMessage("§cArgumentos em falta: /gamemode [0/1/2/3] [jogador]");
            FSound.fail(p);

            return false;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("0")) {
                p.setGameMode(GameMode.SURVIVAL);
                p.sendMessage(MessagesManager.gamemode_change.replace("%gamemode%", GameModeHelper.getGameModeLabel(GameMode.SURVIVAL)));
                FSound.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("1")) {
                p.setGameMode(GameMode.CREATIVE);
                p.sendMessage(MessagesManager.gamemode_change.replace("%gamemode%", GameModeHelper.getGameModeLabel(GameMode.CREATIVE)));
                FSound.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("2")) {
                p.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(MessagesManager.gamemode_change.replace("%gamemode%", GameModeHelper.getGameModeLabel(GameMode.ADVENTURE)));
                FSound.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("3")) {
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(MessagesManager.gamemode_change.replace("%gamemode%", GameModeHelper.getGameModeLabel(GameMode.SPECTATOR)));

                FSound.success(p);

                return false;
            }
        }

        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                p.sendMessage(MessagesManager.jogadorOff);
                FSound.fail(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("0")) {
                target.setGameMode(GameMode.SURVIVAL);
                p.sendMessage(MessagesManager.gamemode_change_others
                        .replace("%gamemode%", GameModeHelper.getGameModeLabel(GameMode.SURVIVAL))
                        .replace("%player%", target.getName()));
                FSound.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("1")) {
                target.setGameMode(GameMode.CREATIVE);
                p.sendMessage(MessagesManager.gamemode_change_others
                        .replace("%gamemode%", GameModeHelper.getGameModeLabel(GameMode.CREATIVE))
                        .replace("%player%", target.getName()));
                FSound.success(p);

                return false;
            }
            if (args[0].equalsIgnoreCase("2")) {
                target.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(MessagesManager.gamemode_change_others
                        .replace("%gamemode%", GameModeHelper.getGameModeLabel(GameMode.ADVENTURE))
                        .replace("%player%", target.getName()));
                FSound.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("3")) {
                target.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(MessagesManager.gamemode_change_others
                        .replace("%gamemode", GameModeHelper.getGameModeLabel(GameMode.SPECTATOR))
                        .replace("%player%", target.getName()));
                FSound.success(p);

                return false;
            }
        }

        return false;
    }
}
