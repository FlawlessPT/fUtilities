package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.managers.MessagesManager;
import pt.flawless.fapi.sounds.FSound;

public class HealCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if (args.length == 0) {
            p.setHealth(20.0);
            p.setFoodLevel(20);
            p.sendMessage(MessagesManager.heal);
            FSound.success(p);

            return false;
        }

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                p.sendMessage(MessagesManager.jogadorOff);
                FSound.fail(p);
            } else {
                target.setHealth(20.0D);
                target.setFoodLevel(20);
                p.sendMessage("§eA §7fome §ee §7vida §ede §7" + target.getName() + " §eforam regeneradas!");
                FSound.success(p);
            }

            return false;
        }
        return false;
    }
}
