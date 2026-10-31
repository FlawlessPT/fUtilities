package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

public class ClearCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length > 1) {
            p.sendMessage("§cUso: /clear [jogador]");
            FSound.fail(p);
            return true;
        }
        Player target = p;
        if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage("§cJogador não encontrado.");
                FSound.fail(p);
                return true;
            }
        }
        target.getInventory().clear();
        target.getInventory().setHelmet(null);
        target.getInventory().setChestplate(null);
        target.getInventory().setLeggings(null);
        target.getInventory().setBoots(null);
        p.sendMessage(target == p ? "§eInventário limpo!" : "§eLimpaste o inventário de §7" + target.getName() + "§e.");
        FSound.success(p);
        return true;
    }
}
