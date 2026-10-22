package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 0) {
            p.sendMessage("§cUso: /enderchest");
            return true;
        }
        p.openInventory(p.getEnderChest());
        return true;
    }
}
