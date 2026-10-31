package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/** {@code /craft}: abre uma mesa de crafting portátil. */
public class CraftCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 0) {
            sender.sendMessage("§cUso: /craft");
            return true;
        }
        p.openWorkbench(null, true);
        return true;
    }
}
