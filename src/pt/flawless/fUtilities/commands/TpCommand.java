package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.managers.MessagesManager;
import pt.flawless.fapi.sounds.FSound;

public class TpCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessagesManager.onlyPlayers);
        } else {
            Player p = (Player) sender;

            if (p.hasPermission("barberians.staff")) {
                if (args.length == 0) {
                    FSound.fail(p);
                    p.sendMessage("§cArgumentos em falta: /tp [jogador]");

                    return false;
                }
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);

                    if (target != null) {
                        p.teleport(target);
                        p.sendMessage("§eFoste teleportado até §7" + target.getName() + "§e.");
                        FSound.success(p);

                        return false;
                    } else {
                        p.sendMessage(MessagesManager.jogadorOff);
                        FSound.fail(p);

                        return false;
                    }
                }

            } else {
                p.sendMessage(MessagesManager.semPermissão);
                FSound.fail(p);

                return false;
            }
        }
        return false;
    }
}
