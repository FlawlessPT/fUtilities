package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

public class DayNightCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        boolean day = command.getName().equalsIgnoreCase("day");
        int time = day ? 1000 : 16000;
        p.getWorld().setStorm(false);
        p.getWorld().setTime(time);
        p.getWorld().setFullTime(time);
        p.sendMessage(day ? "§eTempo definido para §2Dia§e." : "§eTempo definido para §5Noite§e.");
        FSound.success(p);
        return true;
    }
}
