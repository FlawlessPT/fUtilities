package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pt.flawless.fapi.sounds.FSound;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/** {@code /freeze} (tu) e {@code /freeze <jogador>}: alterna o congelamento. O estado só vive em memória. */
public class FreezeCommand implements CommandExecutor, Listener {
    private final Set<UUID> frozen = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage("§cUso: /freeze [jogador]");
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
            sender.sendMessage("§cUso: /freeze <jogador>");
            return true;
        }
        boolean nowFrozen = frozen.add(target.getUniqueId());
        if (!nowFrozen) frozen.remove(target.getUniqueId());
        if (nowFrozen) {
            target.sendMessage("§cFoste congelado.");
            if (!target.equals(sender)) sender.sendMessage("§eCongelaste §7" + target.getName() + "§e.");
        } else {
            target.sendMessage("§aFoste descongelado.");
            if (!target.equals(sender)) sender.sendMessage("§eDescongelaste §7" + target.getName() + "§e.");
        }
        if (sender instanceof Player p) FSound.success(p);
        return true;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!frozen.contains(e.getPlayer().getUniqueId()) || e.getTo() == null) return;
        var from = e.getFrom();
        var to = e.getTo();
        if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {
            to.setX(from.getX());
            to.setY(from.getY());
            to.setZ(from.getZ());
            e.setTo(to);
        }
    }
}
