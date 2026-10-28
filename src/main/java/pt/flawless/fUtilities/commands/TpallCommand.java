package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

/** {@code /tpall} (para ti) e {@code /tpall <jogador>}: teleporta todos os jogadores online para um jogador. */
public class TpallCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage("§cUso: /tpall [jogador]");
            return true;
        }
        Player destination;
        if (args.length == 1) {
            destination = Bukkit.getPlayer(args[0]);
            if (destination == null) {
                sender.sendMessage("§cJogador não encontrado.");
                if (sender instanceof Player p) FSound.fail(p);
                return true;
            }
        } else if (sender instanceof Player p) {
            destination = p;
        } else {
            sender.sendMessage("§cUso: /tpall <jogador>");
            return true;
        }
        int count = 0;
        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other.equals(destination)) continue;
            other.teleport(destination);
            other.sendMessage("§eFoste teleportado para §7" + destination.getName() + "§e.");
            count++;
        }
        sender.sendMessage("§eTeleportaste §7" + count + " §ejogador(es) para §7" + destination.getName() + "§e.");
        if (sender instanceof Player p) FSound.success(p);
        return true;
    }
}
