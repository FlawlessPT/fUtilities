package pt.flawless.FUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pt.flawless.FUtilities.managers.MessagesManager;

public class VidaCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (!(sender instanceof Player))
        {

            sender.sendMessage(MessagesManager.onlyPlayers);

            return true;
        }

        Player p = (Player) sender;

        if (args.length == 0)
        {
            if (p.hasPermission("barberians.staff"))
            {
                if (p.getHealth() < 20.0D)
                {

                    p.setHealth(20.0D);
                    p.sendMessage(MessagesManager.vida);
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 2.0F);

                }
                else
                {

                    p.sendMessage(MessagesManager.vidaFull);
                    p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1.0F, 2.0F);

                }
            }
            else
            {

                p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1.0F, 2.0F);
                p.sendMessage(MessagesManager.semPermissão);

            }
        }

        if (args.length == 1)
        {
            if (p.hasPermission("barberians.staff"))
            {

                Player target = Bukkit.getPlayer(args[0]);

                if (target == null)
                {

                    p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1.0F, 2.0F);
                    p.sendMessage(MessagesManager.jogadorOff);

                    return false;
                }

                if (target.getHealth() < 20.0D)
                {

                    target.setHealth(20.0D);
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 2.0F);
                    p.sendMessage("§eA vida de §7" + target.getName() + " §efoi regenerada!");

                }
                else
                {

                    p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1.0F, 2.0F);
                    p.sendMessage("§cA vida de §7" + target.getName() + " §cnão pode ser regenerada porque o mesmo já se encontra com a vida cheia!");

                }

            }
            else
            {

                p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1.0F, 2.0F);
                p.sendMessage(MessagesManager.semPermissão);

            }
        }
        return false;
    }
}
