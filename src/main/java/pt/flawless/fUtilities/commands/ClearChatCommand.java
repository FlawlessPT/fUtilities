package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

public class ClearChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        for (Player pl : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i <= 200; i++) {
                pl.sendMessage("");
            }
            pl.sendMessage("§eChat limpo por: §7" + p.getName());
        }
        FSound.success(p);
        return true;
    }
}
