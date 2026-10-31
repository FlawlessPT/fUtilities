package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

/** {@code /ping [jogador]}: mostra a latência. */
public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage("§cUso: /ping [jogador]");
            return true;
        }
        Player target;
        if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("§cJogador não encontrado.");
                if (sender instanceof Player p) FSound.fail(p);
                return true;
            }
        } else if (sender instanceof Player p) {
            target = p;
        } else {
            sender.sendMessage("§cUso: /ping <jogador>");
            return true;
        }
        sender.sendMessage("§ePing de §7" + target.getName() + "§e: §7" + target.getPing() + "ms");
        return true;
    }
}
