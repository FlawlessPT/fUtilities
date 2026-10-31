package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length > 1) {
            p.sendMessage("§cUso: /fly [jogador]");
            FSound.fail(p);
            return true;
        }
        if (args.length == 0) {
            boolean on = !p.getAllowFlight();
            p.setAllowFlight(on);
            p.sendMessage(on ? "§eModo de voo §7Ativado§e!" : "§cModo de voo §7Desativado§c!");
            FSound.success(p);
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            p.sendMessage("§cJogador não encontrado.");
            FSound.fail(p);
            return true;
        }
        boolean on = !target.getAllowFlight();
        target.setAllowFlight(on);
        p.sendMessage(on
                ? "§eModo de voo §7ativado §epara §7" + target.getName() + "§e."
                : "§cModo de voo §7desativado §cpara §7" + target.getName() + "§c.");
        FSound.success(p);
        return true;
    }
}
