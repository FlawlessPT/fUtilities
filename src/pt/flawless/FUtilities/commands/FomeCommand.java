package pt.flawless.FUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pt.flawless.FUtilities.utils.SoundEffect;
import pt.flawless.FUtilities.managers.MessagesManager;

public class FomeCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        Player p = (Player) sender;

        if (args.length == 0)
        {
            if (p.getFoodLevel() < 20)
            {

                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 2.0F);
                p.setFoodLevel(20);
                p.sendMessage(MessagesManager.fome);

            }
            else
            {

                SoundEffect.success(p);
                p.sendMessage(MessagesManager.fomeFull);

            }
        }

        if (args.length == 1)
        {

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null)
            {

                p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1.0F, 2.0F);
                p.sendMessage(MessagesManager.jogadorOff);

                return false;
            }

            if (target.getFoodLevel() < 20)
            {

                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 2.0F);
                target.setFoodLevel(20);
                p.sendMessage("§eA fome de §7" + target.getName() + " §efoi saciada!");

            }
            else
            {

                p.sendMessage("§cA fome de §7" + target.getName() + " §cnão pode ser saciada porque o mesmo já se encontra com a fome cheia!");

            }
        }

        return false;
    }

}
