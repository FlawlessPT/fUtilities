package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

public class FeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length > 1) {
            p.sendMessage("§cUso: /feed [jogador]");
            FSound.fail(p);
            return true;
        }
        if (args.length == 0) {
            if (p.getFoodLevel() < 20) {
                p.setFoodLevel(20);
                p.sendMessage("§eA tua fome foi completamente regenerada!");
            } else {
                p.sendMessage("§cA tua fome não foi saciada por já estares com a fome cheia!");
            }
            FSound.success(p);
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            p.sendMessage("§cJogador não encontrado.");
            FSound.fail(p);
            return true;
        }
        target.setFoodLevel(20);
        p.sendMessage("§eA fome de §7" + target.getName() + " §efoi saciada!");
        FSound.success(p);
        return true;
    }
}
