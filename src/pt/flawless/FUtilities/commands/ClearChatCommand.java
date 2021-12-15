package pt.flawless.FUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pt.flawless.FUtilities.managers.MessagesManager;

public class ClearChatCommand implements CommandExecutor
{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {

            sender.sendMessage(MessagesManager.onlyPlayers);

        }
        else
        {

            Player p = (Player) sender;

            for (Player pl : Bukkit.getOnlinePlayers())
            {
                for (int i = 0; i <= 200; i++)
                {
                    pl.sendMessage("");
                }
                pl.sendMessage("§eChat limpo por: §7" + sender.getName());
            }
        }
        return false;
    }
}
