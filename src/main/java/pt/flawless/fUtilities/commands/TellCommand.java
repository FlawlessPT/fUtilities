package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pt.flawless.fapi.sounds.FSound;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** {@code /tell} e {@code /r}: mensagens privadas. O {@code /r} responde ao último com quem se falou. */
public class TellCommand implements CommandExecutor, Listener {
    /** jogador -> último interlocutor. */
    private final Map<UUID, UUID> last = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (command.getName().equalsIgnoreCase("r")) {
            if (args.length < 1) {
                p.sendMessage("§cUso: /r <mensagem>");
                return true;
            }
            UUID id = last.get(p.getUniqueId());
            Player target = id == null ? null : Bukkit.getPlayer(id);
            if (target == null) {
                p.sendMessage("§cNão tens ninguém a quem responder.");
                FSound.fail(p);
                return true;
            }
            send(p, target, String.join(" ", args));
            return true;
        }
        if (args.length < 2) {
            p.sendMessage("§cUso: /tell <jogador> <mensagem>");
            return true;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            p.sendMessage("§cJogador não encontrado.");
            FSound.fail(p);
            return true;
        }
        if (target.equals(p)) {
            p.sendMessage("§cNão podes enviar mensagens a ti próprio.");
            FSound.fail(p);
            return true;
        }
        send(p, target, String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length)));
        return true;
    }

    private void send(Player from, Player to, String message) {
        from.sendMessage("§7[§eTu §7-> §e" + to.getName() + "§7] §f" + message);
        to.sendMessage("§7[§e" + from.getName() + " §7-> §eTu§7] §f" + message);
        last.put(from.getUniqueId(), to.getUniqueId());
        last.put(to.getUniqueId(), from.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        last.remove(e.getPlayer().getUniqueId());
    }
}
