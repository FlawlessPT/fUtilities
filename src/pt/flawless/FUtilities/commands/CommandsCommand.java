package pt.flawless.FUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.FUtilities.api.Som;
import pt.flawless.FUtilities.inventories.CommandsMenu;
import pt.flawless.FUtilities.managers.VariablesManager;

public class CommandsCommand extends VariablesManager implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (player.hasPermission(comandoCommandPermission))
            {
                CommandsMenu.load(player);
                Som.success(player);
            }
            else
            {
                player.sendMessage(noPermissionMessage);
                Som.fail(player);
            }
        }

        return false;
    }
}
