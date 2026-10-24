package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.arenas.Arena;
import pt.flawless.fUtilities.arenas.ArenaStore;
import pt.flawless.fUtilities.arenas.ArenaTeleporter;

import java.util.List;
import java.util.Locale;

public class ArenaCommand implements TabExecutor {
    private final ArenaStore store;

    public ArenaCommand(ArenaStore store) {
        this.store = store;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 1) {
            String names = String.join("§7, §e", store.all().stream().map(Arena::id).toList());
            p.sendMessage("§cUso: /arena <nome>");
            p.sendMessage("§7Arenas: §e" + (names.isEmpty() ? "nenhuma" : names));
            return true;
        }
        ArenaTeleporter.teleport(p, store, args[0]);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) return List.of();
        String prefix = args[0].toLowerCase(Locale.ROOT);
        return store.all().stream().map(Arena::id).filter(id -> id.startsWith(prefix)).toList();
    }
}
