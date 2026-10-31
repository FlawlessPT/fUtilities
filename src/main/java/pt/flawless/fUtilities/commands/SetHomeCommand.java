package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pt.flawless.fUtilities.homes.HomeStore;
import pt.flawless.fapi.sounds.FSound;

import java.util.List;
import java.util.Locale;

public class SetHomeCommand implements TabExecutor {
    private final Plugin plugin;
    private final HomeStore store;

    public SetHomeCommand(Plugin plugin, HomeStore store) {
        this.plugin = plugin;
        this.store = store;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 1) {
            p.sendMessage("§cUso: /sethome <home>");
            return true;
        }
        String name = args[0].toLowerCase(Locale.ROOT);
        // o nome é chave YAML: sem pontos nem espaços
        if (!name.matches("[a-z0-9_-]{1,16}")) {
            p.sendMessage("§cO nome da home só pode ter letras, números, _ e - (máx. 16).");
            FSound.fail(p);
            return true;
        }
        boolean exists = store.get(p.getUniqueId(), name).isPresent();
        int max = plugin.getConfig().getInt("Homes.Max", 3);
        if (!exists && !p.hasPermission("futilities.home.unlimited") && store.all(p.getUniqueId()).size() >= max) {
            p.sendMessage("§cJá tens o máximo de homes (" + max + "). Apaga uma com /delhome.");
            FSound.fail(p);
            return true;
        }
        store.set(p.getUniqueId(), name, p.getLocation());
        p.sendMessage(exists ? "§aHome §e" + name + " §aatualizada." : "§aHome §e" + name + " §adefinida.");
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
