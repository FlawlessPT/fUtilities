package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.managers.MessagesManager;
import pt.flawless.fapi.sounds.FSound;

public class VidaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessagesManager.onlyPlayers);

            return true;
        }

        Player p = (Player) sender;

        if (args.length == 0) {
            if (p.hasPermission("barberians.staff")) {
                p.setHealth(20.0D);
                p.sendMessage(MessagesManager.vida);
                FSound.success(p);
            } else {
                p.sendMessage(MessagesManager.semPermissão);
                FSound.fail(p);
            }
        }

        if (args.length == 1) {
            if (p.hasPermission("barberians.staff")) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    FSound.fail(p);
                    p.sendMessage(MessagesManager.jogadorOff);

                    return false;
                }

                target.setHealth(20.0D);
                p.sendMessage("§eA vida de §7" + target.getName() + " §efoi regenerada!");
                FSound.success(p);
            } else {
                p.sendMessage(MessagesManager.semPermissão);
                FSound.fail(p);
            }
        }
        return false;
    }
}
