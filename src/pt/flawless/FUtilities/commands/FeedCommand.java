package pt.flawless.FUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pt.flawless.FUtilities.managers.MessagesManager;
import pt.flawless.fapi.sounds.FSound;

public class FeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if (args.length == 0) {
            if (p.getFoodLevel() < 20) {
                FSound.success(p);
                p.setFoodLevel(20);
                p.sendMessage(MessagesManager.fome);
            } else {
                FSound.success(p);
                p.sendMessage(MessagesManager.fomeFull);
            }
        }

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                FSound.fail(p);
                p.sendMessage(MessagesManager.jogadorOff);

                return false;
            }
            FSound.success(p);
            target.setFoodLevel(20);
            p.sendMessage("§eA fome de §7" + target.getName() + " §efoi saciada!");
        }

        return false;
    }
}
