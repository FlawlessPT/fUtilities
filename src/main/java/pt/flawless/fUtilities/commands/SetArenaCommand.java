package pt.flawless.fUtilities.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.arenas.ArenaStore;
import pt.flawless.fapi.messages.Messages;
import pt.flawless.fapi.sounds.FSound;

import java.util.Locale;

public class SetArenaCommand implements CommandExecutor {
    private final ArenaStore store;

    public SetArenaCommand(ArenaStore store) {
        this.store = store;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (!p.hasPermission("futilities.arena.admin")) {
            Messages.noPermission(p);
            return true;
        }
        if (args.length != 1 || args[0].contains(".")) {
            p.sendMessage("§cUso: /setarena <nome>");
            return true;
        }
        String id = args[0].toLowerCase(Locale.ROOT);
        Location l = p.getLocation();
        store.setLocation(id, l.getWorld().getName(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
        p.sendMessage("§aArena " + id + " definida.");
        FSound.success(p);
        return true;
    }
}
