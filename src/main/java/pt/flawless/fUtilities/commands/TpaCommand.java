package pt.flawless.fUtilities.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.friends.FriendsApi;
import pt.flawless.fapi.sounds.FSound;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** {@code /tpa}, {@code /tpaccept} e {@code /tpdeny}; os dois últimos também são chamados pelos botões clicáveis. */
public class TpaCommand implements CommandExecutor {
    private static final long EXPIRE_MILLIS = 60_000;

    /** destinatário -> (quem pediu -> validade). */
    private final Map<UUID, Map<UUID, Long>> requests = new HashMap<>();

    /** Nulo sem o fFriends: o fluxo não muda. */
    private final FriendsApi friends;

    public TpaCommand(FriendsApi friends) {
        this.friends = friends;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        switch (command.getName().toLowerCase()) {
            case "tpaccept" -> answer(p, args, true);
            case "tpdeny" -> answer(p, args, false);
            default -> request(p, args);
        }
        return true;
    }

    private void request(Player p, String[] args) {
        if (args.length != 1) {
            p.sendMessage("§cUso: /tpa <jogador>");
            return;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            p.sendMessage("§cJogador não encontrado.");
            FSound.fail(p);
            return;
        }
        if (target.equals(p)) {
            p.sendMessage("§cNão podes enviar um pedido a ti próprio.");
            FSound.fail(p);
            return;
        }
        if (friends != null && friends.canAutoTp(target.getUniqueId(), p.getUniqueId())) {
            p.teleport(target.getLocation());
            p.sendMessage("§aTeleportado para §7" + target.getName() + "§a.");
            target.sendMessage("§e" + p.getName() + " §7teleportou-se para ti.");
            FSound.success(p);
            return;
        }
        requests.computeIfAbsent(target.getUniqueId(), k -> new HashMap<>())
                .put(p.getUniqueId(), System.currentTimeMillis() + EXPIRE_MILLIS);
        p.sendMessage("§aPedido de teleporte enviado a §7" + target.getName() + "§a.");
        target.sendMessage("§e" + p.getName() + " §7quer teleportar-se para ti.");
        target.sendMessage(Component.text("  ")
                .append(button("ACEITAR", NamedTextColor.GREEN, "/tpaccept " + p.getName()))
                .append(Component.text("   "))
                .append(button("RECUSAR", NamedTextColor.RED, "/tpdeny " + p.getName())));
        FSound.success(p);
    }

    private static Component button(String text, NamedTextColor color, String command) {
        return Component.text("[" + text + "]", color, TextDecoration.BOLD)
                .clickEvent(ClickEvent.runCommand(command))
                .hoverEvent(HoverEvent.showText(Component.text("Clica para " + text.toLowerCase(), color)));
    }

    private void answer(Player p, String[] args, boolean accept) {
        Map<UUID, Long> mine = requests.get(p.getUniqueId());
        UUID from = null;
        if (mine != null) {
            long now = System.currentTimeMillis();
            mine.values().removeIf(expiry -> expiry < now);
            if (args.length == 1) {
                Player named = Bukkit.getPlayerExact(args[0]);
                if (named != null && mine.containsKey(named.getUniqueId())) from = named.getUniqueId();
            } else if (mine.size() == 1) {
                from = mine.keySet().iterator().next();
            }
        }
        if (from == null) {
            p.sendMessage(args.length == 0 && mine != null && mine.size() > 1
                    ? "§cTens vários pedidos. Usa /" + (accept ? "tpaccept" : "tpdeny") + " <jogador>."
                    : "§cNão tens nenhum pedido pendente.");
            FSound.fail(p);
            return;
        }
        mine.remove(from);
        Player requester = Bukkit.getPlayer(from);
        if (requester == null) {
            p.sendMessage("§cO jogador já não está online.");
            FSound.fail(p);
            return;
        }
        if (accept) {
            requester.teleport(p.getLocation());
            requester.sendMessage("§a" + p.getName() + " aceitou o teu pedido.");
            p.sendMessage("§aPedido aceite.");
            FSound.success(requester);
        } else {
            requester.sendMessage("§c" + p.getName() + " recusou o teu pedido.");
            p.sendMessage("§cPedido recusado.");
            FSound.fail(requester);
        }
    }
}
