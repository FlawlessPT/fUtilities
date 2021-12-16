package pt.flawless.FUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.FUtilities.utils.SoundEffect;
import pt.flawless.FUtilities.managers.MessagesManager;

public class HealCommand implements CommandExecutor
{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        Player p = (Player) sender;

        if (args.length == 0)
        {
            p.setHealth(20.0);
            p.setFoodLevel(20);
            p.sendMessage(MessagesManager.heal);
            SoundEffect.success(p);

            return false;
        }

        if (args.length == 1)
        {

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null)
            {

                p.sendMessage(MessagesManager.jogadorOff);
                SoundEffect.success(p);

                return false;

            }
            else
            {

                target.setHealth(20.0D);
                target.setFoodLevel(20);
                p.sendMessage("§eA §7fome §ee §7vida §ede §7" + target.getName() + " §eforam regeneradas!");
                SoundEffect.success(p);

                return false;
            }

        }
        return false;
    }
}
