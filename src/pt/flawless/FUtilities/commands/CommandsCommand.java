package pt.flawless.FUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.FUtilities.inventories.CommandsInventory;
import pt.flawless.FUtilities.managers.VariablesManager;
import pt.flawless.FUtilities.utils.SoundEffect;

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
                player.openInventory(CommandsInventory.commandsInventory(player));
                SoundEffect.success(player);
            }
            else
            {
                player.sendMessage(noPermissionMessage);
                SoundEffect.fail(player);
            }
        }

        return false;
    }
}
