package pt.flawless.fUtilities.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

public class DayNightCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            World world = player.getWorld();

            if (command.getName().equalsIgnoreCase("day")) {
                world.setStorm(false);
                world.setTime(1000);
                world.setFullTime(1000);

                player.sendMessage("§eTempo definido para §2Dia§e.");
                FSound.success(player);
            } else {
                world.setStorm(false);
                world.setTime(16000);
                world.setFullTime(16000);

                player.sendMessage("§eTempo definido para §5Noite§e.");
                FSound.success(player);
            }
        }

        return false;
    }
}
