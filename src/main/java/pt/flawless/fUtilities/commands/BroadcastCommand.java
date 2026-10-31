package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;
import pt.flawless.fapi.title.FTitle;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUso: /broadcast <mensagem>");
            if (sender instanceof Player p) {
                FSound.fail(p);
            }
            return true;
        }

        FTitle titleAlert = new FTitle(String.join(" ", args));
        for (Player player : Bukkit.getOnlinePlayers()) {
            titleAlert.setPlayer(player);
            titleAlert.send();
        }
        return true;
    }
}
