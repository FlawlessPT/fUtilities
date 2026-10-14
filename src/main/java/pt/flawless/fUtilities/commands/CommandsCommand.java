package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.menus.CommandsMenu;
import pt.flawless.fUtilities.managers.VariablesManager;
import pt.flawless.fapi.sounds.FSound;

public class CommandsCommand extends VariablesManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (player.hasPermission(comandoCommandPermission)) {
                new CommandsMenu().loadMenu(player);
                FSound.success(player);
            } else {
                player.sendMessage(noPermissionMessage);
                FSound.fail(player);
            }
        }

        return false;
    }
}
