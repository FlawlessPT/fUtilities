package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.managers.MessagesManager;
import pt.flawless.fapi.sounds.FSound;

public class TphereCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessagesManager.onlyPlayers);
        } else {
            Player p = (Player) sender;

            if (p.hasPermission("barberians.staff")) {
                if (args.length == 0) {
                    p.sendMessage("§cArgumentos em falta: /tphere [jogador]");
                    FSound.fail(p);

                    return true;
                }
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);

                    if (target != null) {
                        target.teleport(p);
                        p.sendMessage("§eTeleportaste o jogador §7" + target.getName() + " §eaté ti.");
                        FSound.success(p);
                    } else {
                        p.sendMessage(MessagesManager.jogadorOff);
                        FSound.fail(p);
                    }
                }
            } else {
                p.sendMessage(MessagesManager.semPermissão);
                FSound.fail(p);
            }
        }

        return false;
    }
}
