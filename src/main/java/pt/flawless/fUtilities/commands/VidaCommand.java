package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

public class VidaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length > 1) {
            p.sendMessage("§cUso: /vida [jogador]");
            FSound.fail(p);
            return true;
        }
        if (args.length == 0) {
            p.setHealth(20.0D);
            p.sendMessage("§eA tua vida foi completamente regenerada!");
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
        p.sendMessage("§eA vida de §7" + target.getName() + " §efoi regenerada!");
        FSound.success(p);
        return true;
    }
}
