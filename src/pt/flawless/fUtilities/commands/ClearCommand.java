package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

public class ClearCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                p.getInventory().clear();
                p.getInventory().setHelmet(null);
                p.getInventory().setChestplate(null);
                p.getInventory().setLeggings(null);
                p.getInventory().setBoots(null);
                p.sendMessage("§eInventário limpo!");
                FSound.success(p);
            }

            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    p.sendMessage("§cJogador offline!");
                    FSound.fail(p);

                    return false;
                }

                target.getInventory().clear();
                target.getInventory().setHelmet(null);
                target.getInventory().setChestplate(null);
                target.getInventory().setLeggings(null);
                target.getInventory().setBoots(null);
                target.sendMessage("§eLimpaste o inventário de §7" + target.getName() + "§e.");
                FSound.success(p);
            }
        }
        return false;
    }
}

