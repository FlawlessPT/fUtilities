package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.managers.MessagesManager;
import pt.flawless.fapi.sounds.FSound;

public class FlyCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if (args.length == 0) {
            if (!p.getAllowFlight()) {
                p.setAllowFlight(true);
                p.sendMessage(MessagesManager.flyOn);
                FSound.success(p);
            } else {
                p.setAllowFlight(false);
                p.sendMessage(MessagesManager.flyOff);
                FSound.success(p);
            }
        }

        if (args.length == 1) {
            Player target = Bukkit.getServer().getPlayer(args[0]);

            if (target == null) {
                p.sendMessage(MessagesManager.jogadorOff);
                FSound.fail(p);
            } else {
                if (!target.getAllowFlight()) {
                    target.setAllowFlight(true);
                    p.sendMessage("§eModo de voo §7ativado §epara §7" + target.getName() + "§e.");
                    FSound.success(p);
                } else {
                    target.setAllowFlight(false);
                    p.sendMessage("§cModo de voo §7desativado §cpara §7" + target.getName() + "§c.");
                    FSound.success(p);
                }
            }
        }
        return false;
    }
}
