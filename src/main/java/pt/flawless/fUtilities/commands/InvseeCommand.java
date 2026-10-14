package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.managers.MessagesManager;
import pt.flawless.fapi.sounds.FSound;

public class InvseeCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessagesManager.onlyPlayers);
        } else {
            Player p = (Player) sender;

            if (p.hasPermission("barberians.staff")) {
                if (args.length == 0) {
                    p.sendMessage("§cArgumentos em falta: /invsee [jogador]");

                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);
                if (args.length == 1) {
                    if (target == null) {
                        p.sendMessage(MessagesManager.jogadorOff);
                        FSound.fail(p);

                        return false;

                    } else {
                        p.openInventory(target.getInventory());
                        FSound.success(p);
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
