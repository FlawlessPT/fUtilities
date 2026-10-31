package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.homes.Home;
import pt.flawless.fUtilities.homes.HomeStore;
import pt.flawless.fapi.messages.Messages;
import pt.flawless.fapi.sounds.FSound;

import java.util.List;
import java.util.Locale;

public class HomesCommand implements TabExecutor {
    private final HomeStore store;

    public HomesCommand(HomeStore store) {
        this.store = store;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length > 1) {
            p.sendMessage("§cUso: /homes [jogador]");
            return true;
        }
        OfflinePlayer owner = p;
        if (args.length == 1) {
            if (!p.hasPermission("futilities.home.others")) {
                Messages.noPermission(p);
                return true;
            }
            owner = Bukkit.getOfflinePlayer(args[0]);
            if (!owner.hasPlayedBefore() && !owner.isOnline()) {
                p.sendMessage("§cJogador não encontrado.");
                FSound.fail(p);
                return true;
            }
        }
        List<Home> homes = store.all(owner.getUniqueId());
        if (homes.isEmpty()) {
            p.sendMessage(args.length == 1 ? "§cEsse jogador não tem nenhuma home." : "§cNão tens nenhuma home.");
            return true;
        }
        p.sendMessage("§eHomes: §7" + String.join("§7, §e", homes.stream().map(Home::name).toList()));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1 || !sender.hasPermission("futilities.home.others")) return List.of();
        String prefix = args[0].toLowerCase(Locale.ROOT);
        return Bukkit.getOnlinePlayers().stream().map(Player::getName)
                .filter(n -> n.toLowerCase(Locale.ROOT).startsWith(prefix)).toList();
    }
}
