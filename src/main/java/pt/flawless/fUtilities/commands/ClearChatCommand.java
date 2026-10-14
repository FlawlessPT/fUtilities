package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pt.flawless.fUtilities.managers.MessagesManager;

public class ClearChatCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessagesManager.onlyPlayers);
        } else {
            Player p = (Player) sender;

            if (args.length == 0) {
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    for (int i = 0; i <= 200; i++) {
                        pl.sendMessage("");
                    }
                    pl.sendMessage("§eChat limpo por: §7" + sender.getName());
                }
            } else {

            }
        }
        return false;
    }
}
