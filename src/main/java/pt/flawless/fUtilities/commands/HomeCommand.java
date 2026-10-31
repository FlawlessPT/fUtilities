package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
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

public class HomeCommand implements TabExecutor {
    private final HomeStore store;

    public HomeCommand(HomeStore store) {
        this.store = store;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length > 2) {
            p.sendMessage("§cUso: /home [home] [jogador]");
            return true;
        }
        OfflinePlayer owner = p;
        boolean other = false;
        if (args.length == 2) {
            if (!p.hasPermission("futilities.home.others")) {
                Messages.noPermission(p);
                return true;
            }
            owner = Bukkit.getOfflinePlayer(args[1]);
            if (!owner.hasPlayedBefore() && !owner.isOnline()) {
                p.sendMessage("§cJogador não encontrado.");
                FSound.fail(p);
                return true;
            }
            other = true;
        }
        List<Home> homes = store.all(owner.getUniqueId());
        Home home;
        if (args.length == 0) {
            home = homes.isEmpty() ? null : homes.get(0);
        } else {
            String key = args[0].toLowerCase(Locale.ROOT);
            home = homes.stream().filter(h -> h.name().equals(key)).findFirst().orElse(null);
        }
        if (home == null) {
            p.sendMessage(homes.isEmpty()
                    ? (other ? "§cEsse jogador não tem nenhuma home." : "§cNão tens nenhuma home.")
                    : "§cHome inexistente.");
            FSound.fail(p);
            return true;
        }
        World w = home.world() == null ? null : Bukkit.getWorld(home.world());
        if (w == null) {
            p.sendMessage("§cO mundo da home não está carregado.");
            FSound.fail(p);
            return true;
        }
        p.teleport(new Location(w, home.x(), home.y(), home.z(), home.yaw(), home.pitch()));
        p.sendMessage("§aTeleportado para a home §e" + home.name() + "§a.");
        FSound.success(p);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player p)) return List.of();
        String prefix = args[args.length - 1].toLowerCase(Locale.ROOT);
        if (args.length == 1) {
            return store.all(p.getUniqueId()).stream().map(Home::name).filter(n -> n.startsWith(prefix)).toList();
        }
        if (args.length == 2 && p.hasPermission("futilities.home.others")) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName)
                    .filter(n -> n.toLowerCase(Locale.ROOT).startsWith(prefix)).toList();
        }
        return List.of();
    }
}
