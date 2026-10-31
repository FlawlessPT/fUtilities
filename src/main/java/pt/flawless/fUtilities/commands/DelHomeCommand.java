package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.homes.HomeStore;
import pt.flawless.fapi.sounds.FSound;

import java.util.List;
import java.util.Locale;

public class DelHomeCommand implements TabExecutor {
    private final HomeStore store;

    public DelHomeCommand(HomeStore store) {
        this.store = store;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 1) {
            p.sendMessage("§cUso: /delhome <home>");
            return true;
        }
        if (!store.delete(p.getUniqueId(), args[0])) {
            p.sendMessage("§cNão tens nenhuma home com esse nome.");
            FSound.fail(p);
            return true;
        }
        p.sendMessage("§aHome §e" + args[0].toLowerCase(Locale.ROOT) + " §aapagada.");
        FSound.success(p);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player p) || args.length != 1) return List.of();
        String prefix = args[0].toLowerCase(Locale.ROOT);
        return store.all(p.getUniqueId()).stream().map(h -> h.name()).filter(n -> n.startsWith(prefix)).toList();
    }
}
