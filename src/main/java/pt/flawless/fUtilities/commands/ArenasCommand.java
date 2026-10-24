package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.arenas.ArenaStore;
import pt.flawless.fUtilities.menus.ArenaMenu;

public class ArenasCommand implements CommandExecutor {
    private final ArenaStore store;

    public ArenasCommand(ArenaStore store) {
        this.store = store;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        new ArenaMenu(store, p).open();
        return true;
    }
}
