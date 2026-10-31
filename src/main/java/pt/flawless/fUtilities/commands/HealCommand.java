package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length > 1) {
            p.sendMessage("§cUso: /heal [jogador]");
            FSound.fail(p);
            return true;
        }
        if (args.length == 0) {
            p.setHealth(20.0D);
            p.setFoodLevel(20);
            p.sendMessage("§eA tua fome e vida foram completamente regeneradas!");
            FSound.success(p);
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            p.sendMessage("§cJogador não encontrado.");
            FSound.fail(p);
            return true;
        }
        target.setHealth(20.0D);
        target.setFoodLevel(20);
        p.sendMessage("§eA §7fome §ee §7vida §ede §7" + target.getName() + " §eforam regeneradas!");
        FSound.success(p);
        return true;
    }
}
