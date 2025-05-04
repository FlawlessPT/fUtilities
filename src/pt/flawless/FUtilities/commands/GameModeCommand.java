package pt.flawless.FUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import pt.flawless.FUtilities.managers.MessagesManager;
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
                p.sendMessage(MessagesManager.gm0);
                FSound.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("1")) {
                p.setGameMode(GameMode.CREATIVE);
                p.sendMessage(MessagesManager.gm1);
                FSound.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("2")) {
                p.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(MessagesManager.gm2);
                FSound.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("3")) {
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(MessagesManager.gm3);
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
                p.sendMessage("§eAlteraste o modo de jogo de §7" + target.getName() + " §epara §7Sobrevivência§e!");
                FSound.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("1")) {
                target.setGameMode(GameMode.CREATIVE);
                p.sendMessage("§eAlteraste o modo de jogo de §7" + target.getName() + " §epara §7Criativo§e!");
                FSound.success(p);

                return false;
            }
            if (args[0].equalsIgnoreCase("2")) {
                target.setGameMode(GameMode.ADVENTURE);
                p.sendMessage("§eAlteraste o modo de jogo de §7" + target.getName() + "§epara §7Aventura§e!");
                FSound.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("3")) {
                target.setGameMode(GameMode.SPECTATOR);
                p.sendMessage("§eAlteraste o modo de jogo de §7" + target.getName() + " §epara §7Espectador§e!");
                FSound.success(p);

                return false;
            }
        }

        return false;
    }
}
